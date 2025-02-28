package com.example.hotel.controller;

import com.example.hotel.dto.HotelFilterRequestDTO;
import com.example.hotel.dto.HotelPageResponseDTO;
import com.example.hotel.dto.HotelRequestDTO;
import com.example.hotel.dto.HotelResponseDTO;
import com.example.hotel.entity.Hotel;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelMapper hotelMapper;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<HotelResponseDTO> getHotelById(@PathVariable Long id) {
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(hotelMapper.toResponseDTO(hotel));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponseDTO> createHotel(@RequestBody HotelRequestDTO hotelRequestDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelRequestDTO);
        Hotel createdHotel = hotelService.createHotel(hotel);
        return ResponseEntity.ok(hotelMapper.toResponseDTO(createdHotel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponseDTO> updateHotel(@PathVariable Long id, @RequestBody HotelRequestDTO hotelRequestDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelRequestDTO);
        Hotel updatedHotel = hotelService.updateHotel(id, hotel);
        return ResponseEntity.ok(hotelMapper.toResponseDTO(updatedHotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<HotelResponseDTO>> getAllHotels() {
        List<HotelResponseDTO> hotels = hotelService.getAllHotels().stream()
                .map(hotelMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hotels);
    }
    @PostMapping("/{id}/rate")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<HotelResponseDTO> rateHotel(
            @PathVariable Long id,
            @RequestParam double newMark) {
        Hotel updatedHotel = hotelService.updateHotelRating(id, newMark);
        return ResponseEntity.ok(hotelMapper.toResponseDTO(updatedHotel));
    }
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<HotelPageResponseDTO> getHotelsWithFilter(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Double distanceFromCenter,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Integer ratingCount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        HotelFilterRequestDTO filter = new HotelFilterRequestDTO();
        filter.setId(id);
        filter.setName(name);
        filter.setTitle(title);
        filter.setCity(city);
        filter.setAddress(address);
        filter.setDistanceFromCenter(distanceFromCenter);
        filter.setRating(rating);
        filter.setRatingCount(ratingCount);

        HotelPageResponseDTO response = hotelService.getHotelsWithFilter(filter, page, size);
        return ResponseEntity.ok(response);
    }
}