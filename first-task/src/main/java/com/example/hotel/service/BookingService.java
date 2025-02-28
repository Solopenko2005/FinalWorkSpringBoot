package com.example.hotel.service;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.User;
import com.example.hotel.exception.BadRequestException;
import com.example.hotel.exception.ResourceNotFoundException;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.statistics.event.RoomBookingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafkaTemplate<String, RoomBookingEvent> kafkaTemplate;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking createBooking(Long roomId, Long userId, LocalDate checkInDate, LocalDate checkOutDate) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + roomId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (checkInDate.isAfter(checkOutDate) || checkInDate.isEqual(checkOutDate)) {
            throw new BadRequestException("Check-in date must be before check-out date");
        }

        List<Booking> existingBookings = bookingRepository.findByRoomId(roomId);
        for (Booking existingBooking : existingBookings) {
            if (checkInDate.isBefore(existingBooking.getCheckOutDate()) && checkOutDate.isAfter(existingBooking.getCheckInDate())) {
                throw new BadRequestException("Room is already booked for the specified dates");
            }
        }

        Booking booking = new Booking();
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setRoom(room);
        booking.setUser(user);
        RoomBookingEvent event = new RoomBookingEvent();
        event.setUserId(booking.getUser().getId());
        event.setCheckInDate(booking.getCheckInDate().atStartOfDay());
        event.setCheckOutDate(booking.getCheckOutDate().atStartOfDay());
        event.setTimestamp(LocalDateTime.now());
        kafkaTemplate.send("room-booking-topic", event);

        return bookingRepository.save(booking);
    }


    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }
}