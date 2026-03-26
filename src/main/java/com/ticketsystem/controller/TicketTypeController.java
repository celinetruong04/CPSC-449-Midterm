package com.ticketsystem.controller;

import com.ticketsystem.entity.TicketType;
import com.ticketsystem.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket-types")
@RequiredArgsConstructor
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @PostMapping
    public ResponseEntity<TicketType> createTicketType(@RequestBody TicketType ticketType) {
        TicketType saved = ticketTypeService.createTicketType(ticketType);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}