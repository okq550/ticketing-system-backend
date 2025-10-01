package org.okq550.ticketing.controllers;

import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.dtos.create.CreateTicketValidationRequestDto;
import org.okq550.ticketing.domain.dtos.create.CreateTicketValidationResponseDto;
import org.okq550.ticketing.domain.entities.TicketValidation;
import org.okq550.ticketing.domain.enums.TicketValidationMethodEnum;
import org.okq550.ticketing.mappers.TicketValidationMapper;
import org.okq550.ticketing.services.TicketValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/ticket-validations")
public class TicketValidationController {
    private final TicketValidationService ticketValidationService;
    private final TicketValidationMapper ticketValidationMapper;

    @PostMapping
    public ResponseEntity<CreateTicketValidationResponseDto> validateTicket(
            @RequestBody CreateTicketValidationRequestDto createTicketValidationRequestDto
    ) {
        TicketValidationMethodEnum method = createTicketValidationRequestDto.getMethod();
        TicketValidation ticketValidation;

        if(method.equals(TicketValidationMethodEnum.MANUAL)) {
            ticketValidation = ticketValidationService.validateTicketManually(createTicketValidationRequestDto.getId());
        }
        else {
            ticketValidation = ticketValidationService.validateTicketByQrCode(createTicketValidationRequestDto.getId());
        }

        return ResponseEntity.ok(
                ticketValidationMapper.toCreateTicketValidationResponseDto(ticketValidation)
        );
    }
}
