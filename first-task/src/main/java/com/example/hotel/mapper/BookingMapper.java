package com.example.hotel.mapper;

import com.example.hotel.dto.BookingRequestDTO;
import com.example.hotel.dto.BookingResponseDTO;
import com.example.hotel.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "user.id", source = "userId")
    Booking toEntity(BookingRequestDTO bookingRequestDTO);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "userId", source = "user.id")
    BookingResponseDTO toResponseDTO(Booking booking);
}