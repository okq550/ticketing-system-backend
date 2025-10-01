package org.okq550.ticketing.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.entities.QrCode;
import org.okq550.ticketing.domain.entities.Ticket;
import org.okq550.ticketing.domain.entities.TicketValidation;
import org.okq550.ticketing.domain.enums.QrCodeStatusEnum;
import org.okq550.ticketing.domain.enums.TicketValidationMethodEnum;
import org.okq550.ticketing.domain.enums.TicketValidationStatusEnum;
import org.okq550.ticketing.exceptions.QrCodeNotFoundException;
import org.okq550.ticketing.exceptions.TicketNotFoundException;
import org.okq550.ticketing.repositories.QrCodeRepository;
import org.okq550.ticketing.repositories.TicketRepository;
import org.okq550.ticketing.repositories.TicketValidationRepository;
import org.okq550.ticketing.services.TicketValidationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImp implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE).orElseThrow(() -> new QrCodeNotFoundException(
                String.format("QRCode with ID %s is not found", qrCodeId)
        ));

        Ticket ticket = qrCode.getTicket();
        return ValidateTicket(ticket, TicketValidationMethodEnum.QR_SCAN);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException(
                String.format("Ticket ID %s is not found", ticketId)
        ));

        return ValidateTicket(ticket, TicketValidationMethodEnum.MANUAL);
    }

    private TicketValidation ValidateTicket(Ticket ticket, TicketValidationMethodEnum method) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(method);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(v -> TicketValidationStatusEnum.VALID.equals(v.getStatus()))
                .findFirst()
                .map(v -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);

        return ticketValidationRepository.save(ticketValidation);
    }
}
