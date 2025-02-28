package com.example.hotel.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoomPageResponseDTO {
    private List<RoomResponseDTO> rooms; // Список комнат
    private long totalElements; // Общее количество записей
    private int totalPages; // Общее количество страниц
}