package org.okq550.ticketing.services;

import com.google.zxing.WriterException;
import org.okq550.ticketing.domain.entities.QrCode;
import org.okq550.ticketing.domain.entities.Ticket;

import java.io.IOException;

public interface QrCodeService {
    QrCode generateQrCode(Ticket ticket);
}
