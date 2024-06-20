package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.ReportDto;
import org.javaacademy.afisha.entity.Report;
import org.javaacademy.afisha.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    public List<ReportDto> getReportGroupedByEvents() {
        return reportRepository.getReports().stream().map(this::convertToDto).toList();
    }

    private ReportDto convertToDto(Report entity) {
        return ReportDto.builder()
                .eventName(entity.getEventName())
                .eventTypeName(entity.getEventTypeName())
                .countSelledTickets(entity.getCountSelledTickets())
                .sumTicketPrices(entity.getSumTicketPrices())
                .build();
    }
}
