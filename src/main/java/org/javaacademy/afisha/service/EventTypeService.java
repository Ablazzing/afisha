package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;

    public Optional<EventType> findById(Long id) {
        return eventTypeRepository.findById(id);
    }
}
