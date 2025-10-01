package org.okq550.ticketing.services;

import org.okq550.ticketing.domain.entities.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface TicketService {
    Page<Ticket> listUserTickets(UUID purchaserId, Pageable pageable);
    Optional<Ticket> getUserTicket(UUID purchasedId, UUID id);
}
