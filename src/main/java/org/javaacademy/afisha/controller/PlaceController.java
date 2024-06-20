package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/place")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<PlaceDto> createPlace(@RequestBody PlaceDto placeDto) {
        return ResponseEntity.status(CREATED)
                .body(placeService.createPlace(placeDto));
    }

    @GetMapping
    public List<PlaceDto> findAllPlaces() {
        return placeService.findAll();
    }
}
