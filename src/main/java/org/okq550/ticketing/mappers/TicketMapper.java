package org.okq550.ticketing.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.okq550.ticketing.domain.dtos.details.GetTicketDetailsResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListTicketResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListTicketTicketTypeResponseDto;
import org.okq550.ticketing.domain.entities.Ticket;
import org.okq550.ticketing.domain.entities.TicketType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    ListTicketTicketTypeResponseDto toListTicketTicketTypeResponseDto(TicketType ticketType);
    ListTicketResponseDto toListTicketResponseDto(Ticket ticket);

    @Mapping(target = "price", source = "ticket.ticketType.price")
    @Mapping(target = "description", source = "ticket.ticketType.description")
    @Mapping(target = "eventName", source = "ticket.ticketType.event.name")
    @Mapping(target = "eventVenue", source = "ticket.ticketType.event.venue")
    @Mapping(target = "eventStart", source = "ticket.ticketType.event.start")
    @Mapping(target = "eventEnd", source = "ticket.ticketType.event.end")
    GetTicketDetailsResponseDto toGetTicketDetailsResponseDto(Ticket ticket);
}