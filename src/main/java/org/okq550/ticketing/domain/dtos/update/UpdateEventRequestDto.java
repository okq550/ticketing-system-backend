package org.okq550.ticketing.domain.dtos.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.okq550.ticketing.domain.enums.EventStatusEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequestDto {
    @NotNull(message = "id is required")
    private UUID id;
    @NotBlank(message = "name is required")
    private String name;
    private String start;
    private String end;
    @NotBlank(message = "venue is required")
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    @NotNull(message = "status is required")
    private EventStatusEnum status;
    @NotEmpty(message = "ticket type is required")
    @Valid
    private List<UpdateTicketTypeRequestDto> ticketTypes = new ArrayList<>();
}
