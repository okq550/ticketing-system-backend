package org.okq550.ticketing.domain.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.okq550.ticketing.domain.enums.EventStatusEnum;
import org.okq550.ticketing.domain.enums.TicketStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTicketResponseDto {
    private UUID id;
    private TicketStatusEnum status;
    private ListTicketTicketTypeResponseDto ticketType;
}
