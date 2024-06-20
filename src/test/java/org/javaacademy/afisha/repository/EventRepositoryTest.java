package org.javaacademy.afisha.repository;

import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.entity.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    public void createEvent() {
        Event newEvent = Event.builder()
                .name("Ivanushki")
                .placeId(placeId)
                .eventDate(LocalDateTime.now())
                .eventTypeId(theatreEventTypeId)
                .build();
        eventRepository.save(newEvent);

        Event event = eventRepository.findAll().stream()
                .filter(e -> "Ivanushki".equals(e.getName()))
                .findFirst()
                .orElseThrow();
        assertEquals("Ivanushki", event.getName());
        assertEquals(placeId, event.getPlaceId());
        assertEquals(theatreEventTypeId, event.getEventTypeId());
    }
}
