package org.okq550.ticketing.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.okq550.ticketing.domain.dtos.create.CreateTicketValidationResponseDto;
import org.okq550.ticketing.domain.entities.TicketValidation;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketValidationMapper {
    @Mapping(target = "ticketId", source = "ticket.id")
    CreateTicketValidationResponseDto toCreateTicketValidationResponseDto(TicketValidation ticketValidation);
}
