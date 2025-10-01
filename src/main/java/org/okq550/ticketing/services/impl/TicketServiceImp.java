package org.okq550.ticketing.services.impl;

import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.entities.Ticket;
import org.okq550.ticketing.repositories.TicketRepository;
import org.okq550.ticketing.services.TicketService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImp implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Page<Ticket> listUserTickets(UUID purchaserId, Pageable pageable) {
        return ticketRepository.findByPurchaserId(purchaserId, pageable);
    }

    @Override
    public Optional<Ticket> getUserTicket(UUID purchasedId, UUID id) {
        return ticketRepository.findByPurchaserIdAndId(purchasedId, id);
    }
}
