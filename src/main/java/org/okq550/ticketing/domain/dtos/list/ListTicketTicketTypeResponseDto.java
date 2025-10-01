package org.okq550.ticketing.domain.dtos.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.okq550.ticketing.domain.enums.TicketStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListTicketTicketTypeResponseDto {
    private UUID id;
    private String name;
    private Double price;
}
