package org.okq550.ticketing.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.domain.entities.TicketType;
import org.okq550.ticketing.domain.entities.User;
import org.okq550.ticketing.domain.enums.EventStatusEnum;
import org.okq550.ticketing.domain.requests.CreateEventRequest;
import org.okq550.ticketing.domain.requests.UpdateEventRequest;
import org.okq550.ticketing.domain.requests.UpdateTicketTypeRequest;
import org.okq550.ticketing.exceptions.EventNotFoundException;
import org.okq550.ticketing.exceptions.EventUpdateException;
import org.okq550.ticketing.exceptions.TicketTypeNotFoundException;
import org.okq550.ticketing.exceptions.UserNotFoundException;
import org.okq550.ticketing.repositories.EventRepository;
import org.okq550.ticketing.repositories.UserRepository;
import org.okq550.ticketing.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerId).orElseThrow(() ->
                new UserNotFoundException(String.format("User '%s' not found", organizerId)));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType ticketTypeToCreate = new TicketType();
                    ticketTypeToCreate.setName(ticketType.getName());
                    ticketTypeToCreate.setPrice(ticketType.getPrice());
                    ticketTypeToCreate.setDescription(ticketType.getDescription());
                    ticketTypeToCreate.setTotalAvailableTickets(ticketType.getTotalAvailableTickets());
                    ticketTypeToCreate.setEvent(eventToCreate);
                    return ticketTypeToCreate;
                }).toList();


        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);
    }

    @Override
    public Page<Event> listEvents(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventByOrganizerIdAndId(UUID organizerId, UUID id) {
        return eventRepository.findByOrganizerIdAndId(organizerId, id);
    }

    @Override
    @Transactional
    public Event updateEvent(UUID organizerId, UUID id, UpdateEventRequest event) {
        if(event.getId() == null) {
            throw new EventUpdateException("Event ID cannot be null");
        }

        if(!id.equals(event.getId())) {
            throw new EventUpdateException("Cannot update event");
        }

        Event existingEvent = eventRepository.findByOrganizerIdAndId(organizerId, id).orElseThrow(
                () -> new EventNotFoundException(String.format("Event with ID '%s' does not exist", id))
        );

        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setStatus(event.getStatus());

        // Get all IDs from the event request
        Set<UUID> requestTicketTypeIds = event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // Remove ticketType IDs from the existingEvent where the ID is not in the requestTicketTypeIds.
        // Meaning the user did not send it leading to delete it from the existingEvent object.
        existingEvent.getTicketTypes()
                .removeIf(existingTicketType -> !requestTicketTypeIds.contains(existingTicketType.getId()));
        
        Map<UUID, TicketType> existingTicketTypeIndex = existingEvent.getTicketTypes()
                .stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));
        
        for(UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if(ticketType.getId() == null) {
                // Create new ticket type case
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailableTickets(ticketType.getTotalAvailableTickets());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);
            } else if (existingTicketTypeIndex.containsKey(ticketType.getId())) {
                // Update ticket type case
                TicketType existingTicketType = existingTicketTypeIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailableTickets(ticketType.getTotalAvailableTickets());
            }
            else {
                throw new TicketTypeNotFoundException(
                        String.format("Ticket type with ID '%s' does not exist", ticketType.getId())
                );
            }
        }

        return eventRepository.save(existingEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(UUID organizerId, UUID id) {
        getEventByOrganizerIdAndId(organizerId, id).ifPresent(eventRepository::delete);
    }

    @Override
    public Page<Event> listPublishedEvents(Pageable pageable) {
        return eventRepository.findByStatus(EventStatusEnum.PUBLISHED, pageable);
    }

    @Override
    public Page<Event> searchPublishedEvents(String query, Pageable pageable) {
        return eventRepository.searchEvents(query, pageable);
    }

    @Override
    public Optional<Event> getPublishedEvent(UUID id) {
        return eventRepository.findByIdAndStatus(id, EventStatusEnum.PUBLISHED);
    }
}
