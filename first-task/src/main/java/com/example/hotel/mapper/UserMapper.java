package com.example.hotel.mapper;

import com.example.hotel.dto.UserRequestDTO;
import com.example.hotel.dto.UserResponseDTO;
import com.example.hotel.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(UserRequestDTO userRequestDTO);

    UserResponseDTO toResponseDTO(User user);
}