package com.ticketsystem.service;

import com.ticketsystem.entity.Event;
import com.ticketsystem.entity.TicketType;
import com.ticketsystem.repository.EventRepository;
import com.ticketsystem.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final EventRepository eventRepository;

    public TicketType createTicketType(TicketType ticketType) {
        Event event = eventRepository.findById(ticketType.getEvent().getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        ticketType.setEvent(event);
        return ticketTypeRepository.save(ticketType);
    }
}