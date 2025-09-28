package org.okq550.ticketing.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.okq550.ticketing.domain.dtos.details.GetPublishedEventDetailsResponseDto;
import org.okq550.ticketing.domain.dtos.create.CreateEventRequestDto;
import org.okq550.ticketing.domain.dtos.create.CreateEventResponseDto;
import org.okq550.ticketing.domain.dtos.create.CreateTicketTypeRequestDto;
import org.okq550.ticketing.domain.dtos.details.GetEventDetailsResponseDto;
import org.okq550.ticketing.domain.dtos.details.GetEventDetailsTicketTypesResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListEventResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListEventTicketTypeResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListPublishedEventResponseDto;
import org.okq550.ticketing.domain.dtos.update.UpdateEventRequestDto;
import org.okq550.ticketing.domain.dtos.update.UpdateEventResponseDto;
import org.okq550.ticketing.domain.dtos.update.UpdateTicketTypeRequestDto;
import org.okq550.ticketing.domain.dtos.update.UpdateTicketTypeResponseDto;
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

    ListPublishedEventResponseDto toListPublishedEventResponseDto(Event event);
    GetPublishedEventDetailsResponseDto toGetPublishedEventDetailsResponseDto(Event event);
}
