package com.example.statistics.service;

import com.example.statistics.model.StatisticEvent;
import com.example.statistics.repository.StatisticRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @KafkaListener(topics = "user-registration", groupId = "statistics-group")
    public void handleUserRegistration(String userId) {
        StatisticEvent event = new StatisticEvent();
        event.setEventType("REGISTRATION");
        event.setUserId(userId);
        event.setEventDate(LocalDateTime.now());
        statisticRepository.save(event);
    }

    @KafkaListener(topics = "room-booking", groupId = "statistics-group")
    public void handleRoomBooking(String bookingDetails) {
        StatisticEvent event = new StatisticEvent();
        event.setEventType("BOOKING");
        event.setBookingDetails(bookingDetails);
        event.setEventDate(LocalDateTime.now());
        statisticRepository.save(event);
    }
}