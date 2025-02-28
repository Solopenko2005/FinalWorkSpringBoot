package com.example.hotel.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class RoomRequestDTO {
    private String name;
    private String description;
    private String roomNumber;
    private double price;
    private int maxPeople;
    private List<LocalDate> unavailableDates;
    private Long hotelId;
}