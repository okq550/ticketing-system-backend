package org.okq550.ticketing.controllers;

import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.dtos.details.GetTicketDetailsResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListTicketResponseDto;
import org.okq550.ticketing.mappers.TicketMapper;
import org.okq550.ticketing.services.QrCodeService;
import org.okq550.ticketing.services.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.okq550.ticketing.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;
    private final QrCodeService qrCodeService;
    private final TicketMapper ticketMapper;

    @GetMapping
    public Page<ListTicketResponseDto> listUserTickets(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        return ticketService.listUserTickets(parseUserId(jwt), pageable).map(ticketMapper::toListTicketResponseDto);
    }

    @GetMapping(path = "/{ticketId}")
    public ResponseEntity<GetTicketDetailsResponseDto> getTicketDetails(@AuthenticationPrincipal Jwt jwt,
                                                                        @PathVariable UUID ticketId) {
        UUID userId = parseUserId(jwt);
        return ticketService.getUserTicket(userId, ticketId)
                .map(ticketMapper::toGetTicketDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{ticketId}/qr-codes")
    public ResponseEntity<byte[]> getTicketQrCode(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketId
    ) {
        byte[] qrCodeImage = qrCodeService.getQrCodeImageForUserAndTicket(parseUserId(jwt), ticketId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(qrCodeImage.length);

        return ResponseEntity.ok().headers(headers).body(qrCodeImage);
    }
}
