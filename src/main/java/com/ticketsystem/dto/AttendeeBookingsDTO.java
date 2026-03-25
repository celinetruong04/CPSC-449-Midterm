package com.ticketsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendeeBookingsDTO {
    private String attendeeName;
    private List<BookingResponseDTO> bookings;
}
