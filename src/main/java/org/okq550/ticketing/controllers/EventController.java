package org.okq550.ticketing.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.dtos.CreateEventRequestDto;
import org.okq550.ticketing.domain.dtos.CreateEventResponseDto;
import org.okq550.ticketing.domain.dtos.GetEventDetailsResponseDto;
import org.okq550.ticketing.domain.dtos.ListEventResponseDto;
import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.domain.requests.CreateEventRequest;
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

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
            ) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = parseUUID(jwt);
        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(@AuthenticationPrincipal Jwt jwt, Pageable pageable) {
        UUID userId = parseUUID(jwt);
        Page<Event> events = eventService.listEvents(userId, pageable);
        return ResponseEntity.ok(events.map(eventMapper::toListEventResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEventDetails(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        UUID userId = parseUUID(jwt);
        return eventService.getEventByOrganizerIdAndId(userId, eventId).map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private UUID parseUUID(Jwt jwt) {
        return UUID.fromString(jwt.getSubject());
    }
}
