package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoRq {
    private String name;
    private Long eventTypeId;
    private Long placeId;
    private LocalDateTime eventDate;
    private BigDecimal price;
}
