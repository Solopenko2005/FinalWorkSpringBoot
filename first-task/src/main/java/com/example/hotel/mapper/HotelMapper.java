package com.example.hotel.mapper;

import com.example.hotel.dto.HotelRequestDTO;
import com.example.hotel.dto.HotelResponseDTO;
import com.example.hotel.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HotelMapper {
    HotelMapper INSTANCE = Mappers.getMapper(HotelMapper.class);

    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    Hotel toEntity(HotelRequestDTO hotelRequestDTO);

    HotelResponseDTO toResponseDTO(Hotel hotel);
}