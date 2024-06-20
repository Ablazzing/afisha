package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private String eventName;
    private String eventTypeName;
    private Integer countSelledTickets;
    private BigDecimal sumTicketPrices;
}
