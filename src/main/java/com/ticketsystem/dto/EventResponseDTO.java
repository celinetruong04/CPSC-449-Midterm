package com.ticketsystem.dto;

import com.ticketsystem.entity.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime eventData;
    private EventStatus status;
    private String organizerName;
    private String venueName;
    private List<TicketTypeDTO> ticketTypes;
}
