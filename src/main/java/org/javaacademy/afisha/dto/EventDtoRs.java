package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoRs {
    private Long id;
    private String name;
    private Long eventTypeId;
    private Long placeId;
    private LocalDateTime eventDate;
}
