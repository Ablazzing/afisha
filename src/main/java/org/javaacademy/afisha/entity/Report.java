package org.javaacademy.afisha.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Report {
    private String eventName;
    private String eventTypeName;
    private Integer countSelledTickets;
    private BigDecimal sumTicketPrices;
}
