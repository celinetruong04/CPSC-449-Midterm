package com.ticketsystem.service;

import com.ticketsystem.dto.AttendeeBookingsDTO;
import com.ticketsystem.dto.BookingResponseDTO;
import com.ticketsystem.entity.Attendee;
import com.ticketsystem.repository.AttendeeRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AttendeeService {
    
    private final AttendeeRepository attendeeRepository;

    public AttendeeService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public Attendee registerAttendee(Attendee attendee) {
        if (attendeeRepository.findByEmail(attendee.getEmail()).isPresent()) {
            throw new RuntimeException("Attendee with this email already exists.");
        }
        return attendeeRepository.save(attendee);
    }

    public AttendeeBookingsDTO getAttendeeBookings(Long attendeeId) {
        Attendee attendee = attendeeRepository.findById(attendeeId).orElseThrow(() -> new RuntimeException("Attendee not found"));

        return new AttendeeBookingsDTO(
            attendee.getName(),
            attendee.getBookings().stream().map(b -> new BookingResponseDTO(
                b.getBookingReference(),
                b.getBookingDate(),
                b.getPaymentStatus(),
                attendee.getName(),
                b.getTicketType().getEvent().getTitle(),
                b.getTicketType().getName(),
                b.getTicketType().getPrice()
            ))
            .collect(Collectors.toList())
        );
    }
}
