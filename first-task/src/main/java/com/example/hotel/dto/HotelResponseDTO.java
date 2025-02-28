package com.example.hotel.dto;

import lombok.Data;

@Data
public class HotelResponseDTO {
    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private double distanceFromCenter;
    private double rating;
    private int ratingCount;
}