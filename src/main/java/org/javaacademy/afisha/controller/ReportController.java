package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.ReportDto;
import org.javaacademy.afisha.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public List<ReportDto> getReportByEvents() {
        return reportService.getReportGroupedByEvents();
    }
}
