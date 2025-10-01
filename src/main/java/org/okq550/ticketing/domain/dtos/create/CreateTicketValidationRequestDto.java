package org.okq550.ticketing.domain.dtos.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.okq550.ticketing.domain.enums.TicketValidationMethodEnum;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketValidationRequestDto {
    private UUID id; // Could be QRCode ID or Ticket ID based on the validation method.
    // If we use QR_SCAN method, The ID would be the QRCode ID, Otherwise, It will be the TicketID.
    private TicketValidationMethodEnum method;
}
