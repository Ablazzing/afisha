package org.javaacademy.afisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private Long id;
    private String name;
    private Long eventTypeId;
    private Long placeId;
    private LocalDateTime eventDate;
}
