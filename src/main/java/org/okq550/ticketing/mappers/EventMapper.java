package org.okq550.ticketing.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.okq550.ticketing.domain.dtos.*;
import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.domain.entities.TicketType;
import org.okq550.ticketing.domain.requests.CreateEventRequest;
import org.okq550.ticketing.domain.requests.CreateTicketTypeRequest;
import org.okq550.ticketing.domain.requests.UpdateEventRequest;
import org.okq550.ticketing.domain.requests.UpdateTicketTypeRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);
    CreateEventRequest fromDto(CreateEventRequestDto dto);
    CreateEventResponseDto toCreateEventResponseDto(Event event);

    ListEventTicketTypeResponseDto toListEventTicketTypeResponseDto(TicketType ticketType);
    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventDetailsTicketTypesResponseDto toGetEventDetailsTicketTypesResponseDto(TicketType ticketType);
    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);
    UpdateEventRequest fromDto(UpdateEventRequestDto dto);
    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDto(TicketType ticketType);
    UpdateEventResponseDto toUpdateEventResponseDto(Event event);
}
