package org.javaacademy.afisha.repository;

import org.javaacademy.afisha.AbstractIntegrationTest;
import org.javaacademy.afisha.entity.Place;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlaceRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    private PlaceRepository placeRepository;

    @Test
    public void createAndFind() {
        Place newPlace = Place.builder().city("Moscow").address("Lenina 2").name("Opera").build();
        placeRepository.save(newPlace);

        Place place = placeRepository.findAll().stream()
                .filter(e -> "Opera".equals(e.getName()))
                .findFirst()
                .orElseThrow();
        assertEquals(place.getCity(), "Moscow");
        assertEquals(place.getAddress(), "Lenina 2");
        assertEquals(place.getName(), "Opera");
    }
}
