package com.ticketsystem.repository;


import com.ticketsystem.entity.Booking;
import com.ticketsystem.entity.Attendee;
import com.ticketsystem.entity.TicketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    // check if an attendee already booked a ticket type
    boolean existsByAttendeeAndTicketType(Attendee attendee, TicketType ticketType);

    // get all bookings for a specific attendee
    List<Booking> findByAttendeeId(Long attendeeId);

    // custom query: sum price of confirmed bookings for an event
    @Query("SELECT SUM(b.ticketType.price) FROM Booking b " + "WHERE b.ticketType.event.id = :eventId AND b.paymentStatus = 'CONFIRMED'")
    Double calculateRevenueForEvent(@Param("eventId") Long eventId);
}
