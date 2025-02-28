package com.example.hotel.dto;

import lombok.Data;

@Data
public class HotelFilterRequestDTO {
    private Long id; // ID отеля
    private String name; // Название отеля
    private String title; // Заголовок объявления
    private String city; // Город
    private String address; // Адрес
    private Double distanceFromCenter; // Расстояние до центра
    private Double rating; // Рейтинг
    private Integer ratingCount; // Количество оценок
}