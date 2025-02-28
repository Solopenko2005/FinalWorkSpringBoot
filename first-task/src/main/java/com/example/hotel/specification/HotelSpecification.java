package com.example.hotel.specification;

import com.example.hotel.dto.HotelFilterRequestDTO;
import com.example.hotel.entity.Hotel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HotelSpecification {

    public Specification<Hotel> getHotels(HotelFilterRequestDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }
            if (filter.getName() != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + filter.getName() + "%"));
            }
            if (filter.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
            }
            if (filter.getCity() != null) {
                predicates.add(criteriaBuilder.like(root.get("city"), "%" + filter.getCity() + "%"));
            }
            if (filter.getAddress() != null) {
                predicates.add(criteriaBuilder.like(root.get("address"), "%" + filter.getAddress() + "%"));
            }
            if (filter.getDistanceFromCenter() != null) {
                predicates.add(criteriaBuilder.equal(root.get("distanceFromCenter"), filter.getDistanceFromCenter()));
            }
            if (filter.getRating() != null) {
                predicates.add(criteriaBuilder.equal(root.get("rating"), filter.getRating()));
            }
            if (filter.getRatingCount() != null) {
                predicates.add(criteriaBuilder.equal(root.get("ratingCount"), filter.getRatingCount()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}