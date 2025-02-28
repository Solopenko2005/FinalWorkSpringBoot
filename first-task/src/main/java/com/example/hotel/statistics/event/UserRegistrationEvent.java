package com.example.hotel.statistics.event;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserRegistrationEvent {
    private Long userId;
    private LocalDateTime timestamp;
}