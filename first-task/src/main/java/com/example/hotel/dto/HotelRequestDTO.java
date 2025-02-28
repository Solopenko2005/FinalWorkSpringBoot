package com.example.hotel.dto;

import lombok.Data;

@Data
public class HotelRequestDTO {
    private String name;
    private String title;
    private String city;
    private String address;
    private double distanceFromCenter;
}