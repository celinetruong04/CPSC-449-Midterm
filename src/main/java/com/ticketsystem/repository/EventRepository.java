package com.ticketsystem.repository;

import com.ticketsystem.entity.Event;
import com.ticketsystem.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStatus(EventStatus status);
}
