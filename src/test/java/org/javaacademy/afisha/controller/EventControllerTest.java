package org.javaacademy.afisha.controller;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.dto.EventDtoRq;
import org.javaacademy.afisha.dto.EventDtoRs;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.repository.TicketRepository;
import org.javaacademy.afisha.service.EventTypeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.math.BigDecimal.TEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class EventControllerTest extends AbstractIntegrationTest {
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @SneakyThrows
    public void createEvent() {
        EventDtoRq eventDtoRq = EventDtoRq.builder()
                .eventTypeId(theatreEventTypeId)
                .placeId(placeId)
                .name("Ivanushki")
                .price(TEN)
                .eventDate(LocalDateTime.now())
                .build();

        EventDtoRs eventDtoRs = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(eventDtoRq)
                .log().all()
                .post("/event")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().as(EventDtoRs.class);

        assertEquals("Ivanushki", eventDtoRs.getName());
        assertEquals(theatreEventTypeId, eventDtoRs.getEventTypeId());
        assertEquals(placeId, eventDtoRs.getPlaceId());
        List<Ticket> tickets = ticketRepository.findAll().stream()
                .filter(e -> Objects.equals(eventDtoRs.getId(), e.getEventId()))
                .toList();
        assertEquals(EventTypeEnum.THEATRE.getCountTickets(), tickets.size());
        assertTrue(tickets.stream().allMatch(e -> e.getClientEmail() == null));
    }

    @Test
    void findById() {
        EventDtoRs eventDtoRs = RestAssured.given()
                .log().all()
                .get("/event/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().as(EventDtoRs.class);

        assertEquals("Birthday Malezhik", eventDtoRs.getName());
        assertEquals(LocalDateTime.of(2024, 06,01,0, 0) , eventDtoRs.getEventDate());
    }

    @Test
    void findAll() {
        TypeRef<List<EventDtoRs>> typeRef = new TypeRef<>(){};
        List<EventDtoRs> dtoList = RestAssured.given()
                .log().all()
                .get("/event")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().as(typeRef);

        Assertions.assertEquals(1, dtoList.size());
        EventDtoRs eventDtoRs = dtoList.get(0);
        assertEquals("Birthday Malezhik", eventDtoRs.getName());
        assertEquals(LocalDateTime.of(2024, 06,01,0, 0) , eventDtoRs.getEventDate());
    }

}
