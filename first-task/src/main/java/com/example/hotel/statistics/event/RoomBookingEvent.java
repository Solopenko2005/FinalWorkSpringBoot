package com.example.hotel.statistics.event;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoomBookingEvent {
    private Long userId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private LocalDateTime timestamp;
}