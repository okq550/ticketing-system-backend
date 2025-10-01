package org.okq550.ticketing.domain.dtos.details;

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
public class GetTicketDetailsResponseDto {
    private UUID id;
    private TicketStatusEnum status;
    private Double price;
    private String description;
    private String eventName;
    private String eventVenue;
    private String eventStart;
    private String eventEnd;

}
