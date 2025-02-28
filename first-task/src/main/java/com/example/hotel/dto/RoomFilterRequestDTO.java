package com.example.hotel.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RoomFilterRequestDTO {
    private Long id; // ID комнаты
    private String title; // Заголовок
    private Double minPrice; // Минимальная цена
    private Double maxPrice; // Максимальная цена
    private Integer maxPeople; // Количество гостей
    private LocalDate checkInDate; // Дата заезда
    private LocalDate checkOutDate; // Дата выезда
    private Long hotelId; // ID отеля
}