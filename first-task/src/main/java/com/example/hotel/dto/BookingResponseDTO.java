package com.example.hotel.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponseDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private Long userId;
}