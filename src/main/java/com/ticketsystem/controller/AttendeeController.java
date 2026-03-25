package com.ticketsystem.controller;

import com.ticketsystem.dto.AttendeeBookingsDTO;
import com.ticketsystem.entity.Attendee;
import com.ticketsystem.service.AttendeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendees")
public class AttendeeController {
    
    private final AttendeeService attendeeService;

    public AttendeeController(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @PostMapping
    public ResponseEntity<Attendee> registerAttendee(@RequestBody Attendee attendee) {
        Attendee saved = attendeeService.registerAttendee(attendee);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/bookings")
    public ResponseEntity<AttendeeBookingsDTO> getAttendeeBookings(@PathVariable Long id) {
        AttendeeBookingsDTO dto = attendeeService.getAttendeeBookings(id);
        return ResponseEntity.ok(dto);
    }
}
