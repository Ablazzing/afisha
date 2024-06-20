package org.javaacademy.afisha.controller;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.dto.BuyTicketDto;
import org.javaacademy.afisha.dto.EventDtoRs;
import org.javaacademy.afisha.dto.ReportDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ReportControllerTest extends AbstractIntegrationTest {

    @Test
    void getReportWithoutSelledTickets() {
        TypeRef<List<ReportDto>> typeRef = new TypeRef<>(){};
        List<ReportDto> dtoList = RestAssured.given()
                .log().all()
                .get("/report")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().as(typeRef);

        assertEquals(1, dtoList.size());
        ReportDto reportDto = dtoList.get(0);
        assertEquals("Birthday Malezhik", reportDto.getEventName());
        assertEquals("theatre", reportDto.getEventTypeName());
        assertEquals(0, reportDto.getCountSelledTickets());
        assertEquals(0, ZERO.compareTo(reportDto.getSumTicketPrices()));
    }

    @Test
    void getReportWithSelledTickets() {
        sellTicket("test@yandex.ru", 1L);
        TypeRef<List<ReportDto>> typeRef = new TypeRef<>(){};
        List<ReportDto> dtoList = RestAssured.given()
                .log().all()
                .get("/report")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().as(typeRef);

        assertEquals(1, dtoList.size());
        ReportDto reportDto = dtoList.get(0);
        assertEquals("Birthday Malezhik", reportDto.getEventName());
        assertEquals("theatre", reportDto.getEventTypeName());
        assertEquals(1, reportDto.getCountSelledTickets());
        assertEquals(0, valueOf(100).compareTo(reportDto.getSumTicketPrices()));
    }

    private void sellTicket(String email, Long eventId) {
        BuyTicketDto buyTicketDto = BuyTicketDto.builder()
                .eventId(eventId)
                .email(email)
                .build();
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(buyTicketDto)
                .post("/ticket/buy")
                .then()
                .statusCode(202);
    }
}
