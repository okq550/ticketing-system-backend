package org.okq550.ticketing.services;

import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.domain.enums.EventStatusEnum;
import org.okq550.ticketing.domain.requests.CreateEventRequest;
import org.okq550.ticketing.domain.requests.UpdateEventRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
    Page<Event> listEvents(UUID organizerId, Pageable pageable);
    Optional<Event> getEventByOrganizerIdAndId(UUID organizerId, UUID id);
    Event updateEvent(UUID organizerId, UUID id, UpdateEventRequest event);
    void deleteEvent(UUID organizerId, UUID id);
    Page<Event> listPublishedEvents(Pageable pageable);
    Page<Event> searchPublishedEvents(String query, Pageable pageable);
    Optional<Event> getPublishedEvent(UUID id);
}
