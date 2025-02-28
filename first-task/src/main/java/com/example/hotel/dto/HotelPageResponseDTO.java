package com.example.hotel.dto;

import lombok.Data;
import java.util.List;

@Data
public class HotelPageResponseDTO {
    private List<HotelResponseDTO> hotels; // Список отелей
    private long totalElements; // Общее количество записей
    private int totalPages; // Общее количество страниц
}