package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDto {
    private Long id;
    private String city;
    private String address;
    private String name;
}
