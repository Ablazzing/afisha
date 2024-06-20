package org.javaacademy.afisha.controller;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.dto.EventDtoRs;
import org.javaacademy.afisha.dto.PlaceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class PlaceControllerTest extends AbstractIntegrationTest {

    @Test
    void createPlace() {
        PlaceDto placeDto = PlaceDto.builder()
                .name("Birds")
                .city("Moscow")
                .address("Presnya 12")
                .build();

        PlaceDto result = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(placeDto)
                .log().all()
                .post("/place")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().as(PlaceDto.class);

        assertEquals("Birds", result.getName());
        assertEquals("Moscow", result.getCity());
        assertEquals("Presnya 12", result.getAddress());
        Assertions.assertNotNull(result.getId());
    }

    @Test
    void findAllPlaces() {
        TypeRef<List<PlaceDto>> typeRef = new TypeRef<>(){};
        List<PlaceDto> dtoList = RestAssured.given()
                .log().all()
                .get("/place")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .body().as(typeRef);

        assertEquals(1, dtoList.size());
        PlaceDto placeDto = dtoList.get(0);
        assertEquals("Club 40", placeDto.getName());
        assertEquals("Moscow", placeDto.getCity());
        assertEquals("Lenina 1", placeDto.getAddress());
        Assertions.assertNotNull(placeDto.getId());
    }
}
