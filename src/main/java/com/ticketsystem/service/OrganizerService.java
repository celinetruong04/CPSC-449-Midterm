package com.ticketsystem.service;

import com.ticketsystem.entity.Organizer;
import com.ticketsystem.repository.OrganizerRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganizerService {
    
    private final OrganizerRepository organizerRepository;

    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public Organizer createOrganizer(Organizer organizer) {
        if (organizerRepository.findByEmail(organizer.getEmail()).isPresent()) {
            throw new RuntimeException("Organizer with this email already exists.");
        }
        return organizerRepository.save(organizer);
    }
}
