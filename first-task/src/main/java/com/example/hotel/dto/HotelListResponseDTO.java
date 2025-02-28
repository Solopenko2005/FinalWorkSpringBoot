package com.example.hotel.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelListResponseDTO {
    private List<HotelResponseDTO> hotels;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}