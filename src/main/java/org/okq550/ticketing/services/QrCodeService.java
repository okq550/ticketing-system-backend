package org.okq550.ticketing.services;

import com.google.zxing.WriterException;
import org.okq550.ticketing.domain.entities.QrCode;
import org.okq550.ticketing.domain.entities.Ticket;

import java.io.IOException;
import java.util.UUID;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
