package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.PlaceDto;
import org.javaacademy.afisha.entity.Place;
import org.javaacademy.afisha.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceDto createPlace(PlaceDto req) {
        Place place = convertToEntity(req);
        placeRepository.save(place);
        return convertToDto(place);
    }

    private Place convertToEntity(PlaceDto dto) {
        return Place.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .city(dto.getCity())
                .id(dto.getId())
                .build();
    }

    private PlaceDto convertToDto(Place place) {
        return PlaceDto.builder()
                .address(place.getAddress())
                .name(place.getName())
                .city(place.getCity())
                .id(place.getId())
                .build();
    }

    public List<PlaceDto> findAll() {
        return placeRepository.findAll().stream().map(this::convertToDto).toList();
    }
}
