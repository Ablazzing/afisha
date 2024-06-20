package org.javaacademy.afisha.controller;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.BuyTicketDto;
import org.javaacademy.afisha.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyTicket(@RequestBody BuyTicketDto buyTicketDto) {
        ticketService.buyTicket(buyTicketDto);
        return ResponseEntity.status(ACCEPTED).build();
    }
}
