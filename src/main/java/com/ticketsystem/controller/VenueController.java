package com.ticketsystem.controller;

import com.ticketsystem.entity.Venue;
import com.ticketsystem.service.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody Venue venue) {
        Venue saved = venueService.createVenue(venue);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
