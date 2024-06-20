package org.javaacademy.afisha.repository;

import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.entity.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventTypeRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Test
    public void createAndFindSuccess() {
        List<EventType> eventsBefore = eventTypeRepository.findAll();
        assertFalse(eventsBefore.stream().anyMatch(eventType -> "Fighting".equals(eventType.getName())));

        EventType newEventType = EventType.builder().name("Fighting").build();
        eventTypeRepository.save(newEventType);

        List<EventType> eventTypeList = eventTypeRepository.findAll();
        assertTrue(eventTypeList.stream().anyMatch(eventType -> "Fighting".equals(eventType.getName())));
    }
}
