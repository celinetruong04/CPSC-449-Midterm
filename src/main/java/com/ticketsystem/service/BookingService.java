package com.ticketsystem.service;

import com.ticketsystem.dto.BookingResponseDTO;
import com.ticketsystem.entity.*;
import com.ticketsystem.repository.AttendeeRepository;
import com.ticketsystem.repository.BookingRepository;
import com.ticketsystem.repository.TicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final AttendeeRepository attendeeRepository;
    private final TicketTypeRepository ticketTypeRepository;

    @Transactional
    public BookingResponseDTO bookTicket(Long attendeeId, Long ticketTypeId) {
        Attendee attendee = attendeeRepository.findById(attendeeId).orElseThrow(() -> new RuntimeException("Attendee not found"));
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId).orElseThrow(() -> new RuntimeException("Ticket type not found"));

        if (ticketType.getQuantityAvailable() <= 0) {
            throw new RuntimeException("Sorry, this ticket type is sold out.");
        }

        if (bookingRepository.existsByAttendeeAndTicketType(attendee, ticketType)) {
            throw new RuntimeException("You have already booked this ticket type.");
        }

        // decrement inventory
        ticketType.setQuantityAvailable(ticketType.getQuantityAvailable() - 1);
        ticketTypeRepository.save(ticketType);

        Booking booking = new Booking();
        booking.setAttendee(attendee);
        booking.setTicketType(ticketType);
        booking.setBookingDate(LocalDateTime.now());
        booking.setPaymentStatus(PaymentStatus.CONFIRMED);

        bookingRepository.save(booking);

        // generate booking reference: TKT-{year}-{zero-padded bookingId}
        String reference = String.format("TKT-%d-%05d", LocalDateTime.now().getYear(), booking. getId());
        booking.setBookingReference(reference);
        bookingRepository.save(booking);

        return mapToDTO(booking);
    }

    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getPaymentStatus() == PaymentStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        booking.setPaymentStatus(PaymentStatus.CANCELLED);

        // restore ticket inventory
        TicketType ticketType = booking.getTicketType();
        ticketType.setQuantityAvailable(ticketType.getQuantityAvailable() + 1);
        ticketTypeRepository.save(ticketType);

        bookingRepository.save(booking);

        return mapToDTO(booking);
    }
    
    private BookingResponseDTO mapToDTO(Booking booking) {
        return new BookingResponseDTO(
            booking.getBookingReference(),
            booking.getBookingDate(),
            booking.getPaymentStatus(),
            booking.getAttendee().getName(),
            booking.getTicketType().getEvent().getTitle(),
            booking.getTicketType().getName(),
            booking.getTicketType().getPrice()
        );
    }
}
