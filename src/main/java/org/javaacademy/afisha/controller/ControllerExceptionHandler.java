package org.javaacademy.afisha.controller;

import org.javaacademy.afisha.exception.TicketNotBoughtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(TicketNotBoughtException.class)
    public ResponseEntity<?> handleTicketNotBought(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body("Не удалось купить билет");
    }
}
