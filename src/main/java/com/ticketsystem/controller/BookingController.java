package com.ticketsystem.controller;

import com.ticketsystem.dto.BookingResponseDTO;
import com.ticketsystem.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> bookTicket(@RequestParam Long attendeeId, @RequestParam Long ticketTypeId) {
        BookingResponseDTO booking = bookingService.bookTicket(attendeeId, ticketTypeId);
        return ResponseEntity.status(201).body(booking);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable Long id) {
        BookingResponseDTO canceled = bookingService.cancelBooking(id);
        return ResponseEntity.ok(canceled);
    }
}
