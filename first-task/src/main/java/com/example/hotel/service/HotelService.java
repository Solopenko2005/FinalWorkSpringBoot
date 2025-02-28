package com.example.hotel.service;

import com.example.hotel.dto.HotelFilterRequestDTO;
import com.example.hotel.dto.HotelPageResponseDTO;
import com.example.hotel.entity.Hotel;
import com.example.hotel.exception.BadRequestException;
import com.example.hotel.exception.ResourceNotFoundException;
import com.example.hotel.mapper.HotelMapper;
import com.example.hotel.repository.HotelRepository;
import com.example.hotel.specification.HotelSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.hotel.dto.HotelResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.stream.Collectors;
@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelSpecification hotelSpecification;
    @Autowired
    private HotelMapper hotelMapper;


    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id));
    }

    public Hotel createHotel(Hotel hotel) {
        if (hotel.getName() == null || hotel.getName().isEmpty()) {
            throw new BadRequestException("Hotel name cannot be empty");
        }
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id));

        if (hotelDetails.getName() == null || hotelDetails.getName().isEmpty()) {
            throw new BadRequestException("Hotel name cannot be empty");
        }

        hotel.setName(hotelDetails.getName());
        hotel.setTitle(hotelDetails.getTitle());
        hotel.setCity(hotelDetails.getCity());
        hotel.setAddress(hotelDetails.getAddress());
        hotel.setDistanceFromCenter(hotelDetails.getDistanceFromCenter());
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + id));
        hotelRepository.delete(hotel);
    }

    public Hotel updateHotelRating(Long hotelId, double newMark) {
        if (newMark < 1 || newMark > 5) {
            throw new BadRequestException("Rating must be between 1 and 5");
        }

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id " + hotelId));

        double currentRating = hotel.getRating();
        int numberOfRatings = hotel.getRatingCount();

        // Вычисляем сумму всех оценок
        double totalRating = currentRating * numberOfRatings;

        // Обновляем сумму оценок
        totalRating = totalRating - currentRating + newMark;

        // Вычисляем новый рейтинг
        double newRating = totalRating / (numberOfRatings + 1);

        // Округляем до одного знака после запятой
        newRating = Math.round(newRating * 10) / 10.0;

        // Обновляем рейтинг и количество оценок
        hotel.setRating(newRating);
        hotel.setRatingCount(numberOfRatings + 1);

        return hotelRepository.save(hotel);
    }
    public HotelPageResponseDTO getHotelsWithFilter(HotelFilterRequestDTO filter, int page, int size) {
        Specification<Hotel> spec = hotelSpecification.getHotels(filter);
        Pageable pageable = PageRequest.of(page, size);

        Page<Hotel> hotelPage = hotelRepository.findAll(spec, pageable);

        List<HotelResponseDTO> hotels = hotelPage.getContent().stream()
                .map(hotelMapper::toResponseDTO)
                .collect(Collectors.toList());

        HotelPageResponseDTO response = new HotelPageResponseDTO();
        response.setHotels(hotels);
        response.setTotalElements(hotelPage.getTotalElements());
        response.setTotalPages(hotelPage.getTotalPages());

        return response;
    }
}