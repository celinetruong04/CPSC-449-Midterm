package com.ticketsystem.service;

import com.ticketsystem.entity.Venue;
import com.ticketsystem.repository.VenueRepository;
import org.springframework.stereotype.Service;

@Service
public class VenueService {
    
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }
}
