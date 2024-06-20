package org.javaacademy.afisha.service;

import lombok.RequiredArgsConstructor;
import org.javaacademy.afisha.dto.BuyTicketDto;
import org.javaacademy.afisha.entity.Event;
import org.javaacademy.afisha.entity.EventType;
import org.javaacademy.afisha.entity.Ticket;
import org.javaacademy.afisha.exception.TicketNotBoughtException;
import org.javaacademy.afisha.repository.EventRepository;
import org.javaacademy.afisha.repository.EventTypeRepository;
import org.javaacademy.afisha.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.javaacademy.afisha.service.EventTypeEnum.MUSEUM;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final EventTypeRepository eventTypeRepository;

    public void createTickets(BigDecimal price, Long eventId, int countTickets) {
        ticketRepository.createTickets(price, eventId, countTickets);
    }

    public void buyTicket(BuyTicketDto buyTicketDto) {
        try {
            Event event = eventRepository.findById(buyTicketDto.getEventId()).orElseThrow();
            EventType eventType = eventTypeRepository.findById(event.getEventTypeId()).orElseThrow();
            EventTypeEnum eventTypeTicket = EventTypeEnum.valueOf(eventType.getName().toUpperCase());
            if (MUSEUM.equals(eventTypeTicket)) {
                createAndBuyMuseumTicket(buyTicketDto.getPrice(), buyTicketDto.getEventId(), buyTicketDto.getEmail());
            } else {
                buyNonMuseumTicket(event.getId(), buyTicketDto.getEmail());
            }
        } catch (Exception e) {
            throw new TicketNotBoughtException(e);
        }
    }

    private void createAndBuyMuseumTicket(BigDecimal price, Long eventId, String email) {
        ticketRepository.createSelledTicket(price, eventId, email);
    }

    private void buyNonMuseumTicket(Long eventId, String email) {
        Ticket ticket = ticketRepository.findFreeTicketByEventId(eventId)
                .orElseThrow();
        ticketRepository.buyTicket(ticket, email);
    }
}
