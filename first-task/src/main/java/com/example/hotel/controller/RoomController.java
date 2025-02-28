package com.example.hotel.controller;

import com.example.hotel.dto.RoomFilterRequestDTO;
import com.example.hotel.dto.RoomPageResponseDTO;
import com.example.hotel.dto.RoomRequestDTO;
import com.example.hotel.dto.RoomResponseDTO;
import com.example.hotel.entity.Room;
import com.example.hotel.mapper.RoomMapper;
import com.example.hotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomMapper roomMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id);
        return ResponseEntity.ok(roomMapper.toResponseDTO(room));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponseDTO> createRoom(@RequestBody RoomRequestDTO roomRequestDTO) {
        Room room = roomMapper.toEntity(roomRequestDTO);
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.ok(roomMapper.toResponseDTO(createdRoom));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponseDTO> updateRoom(@PathVariable Long id, @RequestBody RoomRequestDTO roomRequestDTO) {
        Room room = roomMapper.toEntity(roomRequestDTO);
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(roomMapper.toResponseDTO(updatedRoom));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<RoomResponseDTO>> getAllRooms() {
        List<RoomResponseDTO> rooms = roomService.getAllRooms().stream()
                .map(roomMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(rooms);
    }
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RoomPageResponseDTO> getRoomsWithFilter(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer maxPeople,
            @RequestParam(required = false) LocalDate checkInDate,
            @RequestParam(required = false) LocalDate checkOutDate,
            @RequestParam(required = false) Long hotelId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        RoomFilterRequestDTO filter = new RoomFilterRequestDTO();
        filter.setId(id);
        filter.setTitle(title);
        filter.setMinPrice(minPrice);
        filter.setMaxPrice(maxPrice);
        filter.setMaxPeople(maxPeople);
        filter.setCheckInDate(checkInDate);
        filter.setCheckOutDate(checkOutDate);
        filter.setHotelId(hotelId);

        RoomPageResponseDTO response = roomService.getRoomsWithFilter(filter, page, size);
        return ResponseEntity.ok(response);
    }
}