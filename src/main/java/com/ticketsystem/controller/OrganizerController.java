package com.ticketsystem.controller;

import com.ticketsystem.entity.Organizer;
import com.ticketsystem.service.OrganizerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/organizers")
public class OrganizerController {
    
    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping
    public ResponseEntity<Organizer> creatorOrganizer(@RequestBody Organizer organizer) {
        Organizer saved = organizerService.createOrganizer(organizer);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
