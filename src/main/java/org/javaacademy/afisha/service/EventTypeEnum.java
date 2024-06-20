package org.javaacademy.afisha.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventTypeEnum {
    MUSEUM(null), CINEMA(100), THEATRE(50);
    private final Integer countTickets;
}
