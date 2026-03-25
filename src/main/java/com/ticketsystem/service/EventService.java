package com.ticketsystem.service;

import com.ticketsystem.dto.EventResponseDTO;
import com.ticketsystem.dto.TicketTypeDTO;
import com.ticketsystem.dto.RevenueDTO;
import com.ticketsystem.entity.Event;
import com.ticketsystem.entity.EventStatus;
import com.ticketsystem.entity.Organizer;
import com.ticketsystem.entity.Venue;
import com.ticketsystem.repository.BookingRepository;
import com.ticketsystem.repository.EventRepository;
import com.ticketsystem.repository.OrganizerRepository;
import com.ticketsystem.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EventService {
    
    private final EventRepository eventRepository;
    private final BookingRepository bookingRepository;
    private final OrganizerRepository organizerRepository;
    private final VenueRepository venueRepository;

    public Event createEvent(Event event) {
        Organizer organizer = organizerRepository.findById(event.getOrganizer().getId()).orElseThrow(() -> new RuntimeException("Organizer not found"));

        Venue venue = venueRepository.findById(event.getVenue().getId()).orElseThrow(() -> new RuntimeException("Venue not found"));

        event.setOrganizer(organizer);
        event.setVenue(venue);

        return eventRepository.save(event);
    }
    public List<EventResponseDTO> getUpcomingEvents() {
        List<Event> events = eventRepository.findByStatus(EventStatus.UPCOMING);
        return events.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public EventResponseDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        return mapToDTO(event);
    }

    public RevenueDTO getEventRevenue(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
        Double revenue = bookingRepository.calculateRevenueForEvent(eventId);
        return new RevenueDTO(event.getTitle(), revenue != null ? BigDecimal.valueOf(revenue) : BigDecimal.ZERO);
    }

    private EventResponseDTO mapToDTO(Event event) {
        List<TicketTypeDTO> ticketDTOs = event.getTicketTypes().stream().map(tt -> new TicketTypeDTO(
            tt.getId(),
            tt.getName(),
            tt.getPrice(),
            tt.getQuantityAvailable()
        )).collect(Collectors.toList());

        return new EventResponseDTO(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getEventDate(),
            event.getStatus(),
            event.getOrganizer().getName(),
            event.getVenue().getName(),
            ticketDTOs
        );
    }
}
