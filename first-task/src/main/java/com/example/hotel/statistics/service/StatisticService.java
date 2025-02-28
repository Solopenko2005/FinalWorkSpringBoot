package com.example.hotel.statistics.service;

import com.example.hotel.statistics.entity.StatisticEvent;
import com.example.hotel.statistics.event.RoomBookingEvent;
import com.example.hotel.statistics.event.UserRegistrationEvent;
import com.example.hotel.statistics.repository.StatisticEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatisticService {

    @Autowired
    private StatisticEventRepository statisticEventRepository;

    @KafkaListener(topics = "user-registration-topic")
    public void handleUserRegistrationEvent(UserRegistrationEvent event) {
        StatisticEvent statisticEvent = new StatisticEvent();
        statisticEvent.setEventType("USER_REGISTRATION");
        statisticEvent.setUserId(event.getUserId());
        statisticEvent.setTimestamp(LocalDateTime.now());
        statisticEventRepository.save(statisticEvent);
    }

    @KafkaListener(topics = "room-booking-topic")
    public void handleRoomBookingEvent(RoomBookingEvent event) {
        StatisticEvent statisticEvent = new StatisticEvent();
        statisticEvent.setEventType("ROOM_BOOKING");
        statisticEvent.setUserId(event.getUserId());
        statisticEvent.setCheckInDate(event.getCheckInDate());
        statisticEvent.setCheckOutDate(event.getCheckOutDate());
        statisticEvent.setTimestamp(LocalDateTime.now());
        statisticEventRepository.save(statisticEvent);
    }
}