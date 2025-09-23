package org.okq550.ticketing.services.impl;

import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.domain.entities.TicketType;
import org.okq550.ticketing.domain.entities.User;
import org.okq550.ticketing.domain.requests.CreateEventRequest;
import org.okq550.ticketing.domain.requests.UpdateEventRequest;
import org.okq550.ticketing.exceptions.EventUpdateException;
import org.okq550.ticketing.exceptions.UserNotFoundException;
import org.okq550.ticketing.repositories.EventRepository;
import org.okq550.ticketing.repositories.UserRepository;
import org.okq550.ticketing.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
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
    public Event updateEvent(UUID organizerId, UUID id, UpdateEventRequest event) {
        if(event.getId() == null) {
            throw new EventUpdateException("Event ID cannot be null");
        }

        if()
    }
}
