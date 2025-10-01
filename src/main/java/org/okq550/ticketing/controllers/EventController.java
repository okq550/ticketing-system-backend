package org.okq550.ticketing.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.dtos.create.CreateEventRequestDto;
import org.okq550.ticketing.domain.dtos.create.CreateEventResponseDto;
import org.okq550.ticketing.domain.dtos.details.GetEventDetailsResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListEventResponseDto;
import org.okq550.ticketing.domain.dtos.update.UpdateEventRequestDto;
import org.okq550.ticketing.domain.dtos.update.UpdateEventResponseDto;
import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.domain.requests.CreateEventRequest;
import org.okq550.ticketing.domain.requests.UpdateEventRequest;
import org.okq550.ticketing.mappers.EventMapper;
import org.okq550.ticketing.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.okq550.ticketing.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
            ) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = parseUserId(jwt);
        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toCreateEventResponseDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        UUID userId = parseUserId(jwt);
        Page<Event> events = eventService.listEvents(userId, pageable);
        return ResponseEntity.ok(events.map(eventMapper::toListEventResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEventDetails(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserId(jwt);
        return eventService.getEventByOrganizerIdAndId(userId, eventId).map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> updateEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto
    ) {
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
        UUID userId = parseUserId(jwt);
        Event updatedEvent = eventService.updateEvent(userId, eventId, updateEventRequest);
        UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDto(updatedEvent);
        return ResponseEntity.ok(updateEventResponseDto);
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUserId(jwt);
        eventService.deleteEvent(userId, eventId);
        return ResponseEntity.noContent().build();
    }
}
