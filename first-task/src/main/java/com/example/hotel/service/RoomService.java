package com.example.hotel.service;

import com.example.hotel.dto.RoomFilterRequestDTO;
import com.example.hotel.dto.RoomPageResponseDTO;
import com.example.hotel.dto.RoomResponseDTO;
import com.example.hotel.entity.Room;
import com.example.hotel.exception.ResourceNotFoundException;
import com.example.hotel.mapper.RoomMapper;
import com.example.hotel.repository.RoomRepository;
import com.example.hotel.specification.RoomSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomSpecification roomSpecification;


    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
    }

    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));

        room.setName(roomDetails.getName());
        room.setDescription(roomDetails.getDescription());
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setPrice(roomDetails.getPrice());
        room.setMaxPeople(roomDetails.getMaxPeople());
        room.setUnavailableDates(roomDetails.getUnavailableDates());
        return roomRepository.save(room);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id " + id));
        roomRepository.delete(room);
    }
    public RoomPageResponseDTO getRoomsWithFilter(RoomFilterRequestDTO filter, int page, int size) {
        Specification<Room> spec = roomSpecification.getRooms(filter);
        Pageable pageable = PageRequest.of(page, size);

        Page<Room> roomPage = roomRepository.findAll(spec, pageable);

        List<RoomResponseDTO> rooms = roomPage.getContent().stream()
                .map(roomMapper::toResponseDTO)
                .collect(Collectors.toList());

        RoomPageResponseDTO response = new RoomPageResponseDTO();
        response.setRooms(rooms);
        response.setTotalElements(roomPage.getTotalElements());
        response.setTotalPages(roomPage.getTotalPages());

        return response;
    }
}