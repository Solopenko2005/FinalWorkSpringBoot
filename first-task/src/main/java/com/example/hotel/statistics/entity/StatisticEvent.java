package com.example.hotel.statistics.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "statistic_events")
public class StatisticEvent {
    @Id
    private String id;
    private String eventType;
    private Long userId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private LocalDateTime timestamp;
}