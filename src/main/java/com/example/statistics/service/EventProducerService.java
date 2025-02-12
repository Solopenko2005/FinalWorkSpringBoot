package com.example.statistics.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserRegistrationEvent(String userId) {
        kafkaTemplate.send("user-registration", userId);
    }

    public void sendRoomBookingEvent(String bookingDetails) {
        kafkaTemplate.send("room-booking", bookingDetails);
    }
}