package org.okq550.ticketing.services.impl;

import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.entities.Ticket;
import org.okq550.ticketing.domain.entities.TicketType;
import org.okq550.ticketing.domain.entities.User;
import org.okq550.ticketing.domain.enums.TicketStatusEnum;
import org.okq550.ticketing.exceptions.TicketTypeNotFoundException;
import org.okq550.ticketing.exceptions.TicketsSoldOutException;
import org.okq550.ticketing.exceptions.UserNotFoundException;
import org.okq550.ticketing.repositories.TicketRepository;
import org.okq550.ticketing.repositories.TicketTypeRepository;
import org.okq550.ticketing.repositories.UserRepository;
import org.okq550.ticketing.services.QrCodeService;
import org.okq550.ticketing.services.TicketTypeService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImp implements TicketTypeService {
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;


    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User ID %s was not found", userId)
        ));

        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId).orElseThrow(
                () -> new TicketTypeNotFoundException(
                String.format("Ticket Type ID %s was not found", ticketTypeId)
        ));

        int purchasedTicketsCount = ticketRepository.countByTicketTypeId(ticketTypeId);
        int totalAvailableTicketsCount = ticketType.getTotalAvailableTickets();

        if(purchasedTicketsCount + 1 > totalAvailableTicketsCount) {
            throw new TicketsSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setPurchaser(user);
        ticket.setTicketType(ticketType);
        ticket.setStatus(TicketStatusEnum.PURCHASED);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);
    }
}
