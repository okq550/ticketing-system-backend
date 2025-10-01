package org.okq550.ticketing.domain.dtos.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.okq550.ticketing.domain.enums.TicketValidationStatusEnum;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketValidationResponseDto {
    private UUID TicketId;
    private TicketValidationStatusEnum status;
}
