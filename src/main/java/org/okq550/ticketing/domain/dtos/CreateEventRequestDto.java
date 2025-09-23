package org.okq550.ticketing.domain.dtos;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDto {
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
    private List<CreateTicketTypeRequestDto> ticketTypes = new ArrayList<>();
}
