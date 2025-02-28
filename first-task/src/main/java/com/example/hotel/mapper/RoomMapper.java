package com.example.hotel.mapper;

import com.example.hotel.dto.RoomRequestDTO;
import com.example.hotel.dto.RoomResponseDTO;
import com.example.hotel.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {
    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    @Mapping(target = "hotel.id", source = "hotelId")
    Room toEntity(RoomRequestDTO roomRequestDTO);

    @Mapping(target = "hotelId", source = "hotel.id")
    RoomResponseDTO toResponseDTO(Room room);
}