package com.example.hotel.controller;

import com.example.hotel.dto.BookingRequestDTO;
import com.example.hotel.dto.BookingResponseDTO;
import com.example.hotel.entity.Booking;
import com.example.hotel.mapper.BookingMapper;
import com.example.hotel.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingMapper bookingMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking booking = bookingMapper.toEntity(bookingRequestDTO);
        Booking createdBooking = bookingService.createBooking(
                bookingRequestDTO.getRoomId(),
                bookingRequestDTO.getUserId(),
                bookingRequestDTO.getCheckInDate(),
                bookingRequestDTO.getCheckOutDate()
        );
        return ResponseEntity.ok(bookingMapper.toResponseDTO(createdBooking));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {
        List<BookingResponseDTO> bookings = bookingService.getAllBookings().stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsByUser(@PathVariable Long userId) {
        List<BookingResponseDTO> bookings = bookingService.getBookingsByUser(userId).stream()
                .map(bookingMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookings);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingResponseDTO> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        BookingResponseDTO response = bookingService.getAllBookings(page, size);
        return ResponseEntity.ok(response);
    }
}