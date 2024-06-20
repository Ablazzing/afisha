package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.dto.EventDtoRs;
import org.javaacademy.afisha.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventDtoRs> createEvent(@RequestBody EventDtoRq dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.createEvent(dto));
    }

    @GetMapping
    public List<EventDtoRs> findAll() {
        return eventService.findAll();
    }

    @GetMapping("/{id}")
    public EventDtoRs findById(@PathVariable Long id) {
        return eventService.findById(id);
    }
}
