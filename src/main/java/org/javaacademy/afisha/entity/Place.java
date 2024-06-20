package org.javaacademy.afisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    private Long id;
    private String name;
    private String address;
    private String city;
}
