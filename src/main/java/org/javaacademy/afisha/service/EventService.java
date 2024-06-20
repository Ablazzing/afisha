package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.dto.EventDtoRs;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.repository.EventRepository;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final TransactionTemplate transactionTemplate;
    private final TicketService ticketService;
    private final EventTypeRepository eventTypeRepository;

    public EventDtoRs findById(Long id) {
        return convertToDto(eventRepository.findById(id).orElseThrow());
    }

    public EventDtoRs createEvent(EventDtoRq dto) {
        return transactionTemplate.execute((transactionStatus) -> {
            EventType eventType = eventTypeRepository.findById(dto.getEventTypeId()).orElseThrow();
            EventTypeEnum eventTypeEnum = EventTypeEnum.valueOf(eventType.getName().toUpperCase());
            Event entity = convertToEntity(dto);
            eventRepository.save(entity);
            if (EventTypeEnum.MUSEUM != eventTypeEnum) {
                ticketService.createTickets(dto.getPrice(), entity.getId(), eventTypeEnum.getCountTickets());
            }
            return convertToDto(entity);
        });
    }

    public List<EventDtoRs> findAll() {
        return eventRepository.findAll().stream().map(this::convertToDto).toList();
    }

    private Event convertToEntity(EventDtoRq dto) {
        return Event.builder()
                .name(dto.getName())
                .placeId(dto.getPlaceId())
                .eventDate(dto.getEventDate())
                .eventTypeId(dto.getEventTypeId())
                .build();
    }

    private EventDtoRs convertToDto(Event entity) {
        return EventDtoRs.builder()
                .id(entity.getId())
                .eventTypeId(entity.getEventTypeId())
                .eventDate(entity.getEventDate())
                .name(entity.getName())
                .placeId(entity.getPlaceId())
                .build();
    }
}
