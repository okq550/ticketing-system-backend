package org.okq550.ticketing.controllers;

import lombok.RequiredArgsConstructor;
import org.okq550.ticketing.domain.dtos.details.GetPublishedEventDetailsResponseDto;
import org.okq550.ticketing.domain.dtos.list.ListPublishedEventResponseDto;
import org.okq550.ticketing.domain.entities.Event;
import org.okq550.ticketing.mappers.EventMapper;
import org.okq550.ticketing.services.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable
    ) {
        Page<Event> events;
        if(q != null && !q.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(q, pageable);
        }
        else {
            events = eventService.listPublishedEvents(pageable);
        }
        return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getEventDetails2(
            @PathVariable UUID eventId
    ) {
        return eventService.getPublishedEvent(eventId).map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
