package org.javaacademy.afisha.repository;

import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.entity.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createTicket() {
        Long placeId = jdbcTemplate.queryForObject("select id from place limit 1", Long.class);
        Long eventTypeId = jdbcTemplate.queryForObject("select id from event_type limit 1", Long.class);
        Long eventId = jdbcTemplate.queryForObject("select id from event limit 1", Long.class);

        Ticket newTicket = Ticket.builder()
                .eventId(eventId)
                .clientEmail("test@yandex.ru")
                .isSelled(false)
                .price(TEN)
                .build();
        ticketRepository.save(newTicket);

        Ticket ticket = ticketRepository.findAll().stream()
                .filter(e -> "test@yandex.ru".equals(e.getClientEmail()))
                .findFirst()
                .orElseThrow();

        assertEquals(eventId, ticket.getEventId());
        assertEquals(0, ticket.getPrice().compareTo(TEN));
        assertEquals("test@yandex.ru", ticket.getClientEmail());
    }
}
