package org.javaacademy.afisha.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.dto.BuyTicketDto;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class TicketControllerTest extends AbstractIntegrationTest {
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void buyTicket() {
        String email = "test@yandex.ru";
        BuyTicketDto buyTicketDto = BuyTicketDto.builder()
                .eventId(1L)
                .email(email)
                .build();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(buyTicketDto)
                .post("/ticket/buy")
                .then()
                .statusCode(202);

        Ticket ticket = ticketRepository.findAll().stream()
                .filter(e -> email.equals(e.getClientEmail()) && Objects.equals(1L, e.getEventId()))
                .findAny()
                .orElseThrow();
        assertTrue(ticket.getIsSelled());
        assertEquals(0, BigDecimal.valueOf(100).compareTo(ticket.getPrice()));
    }
}
