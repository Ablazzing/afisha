package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyTicketDto {
    private String email;
    private Long eventId;
    private BigDecimal price;
}
