package com.example.hotel.specification;

import com.example.hotel.dto.RoomFilterRequestDTO;
import com.example.hotel.entity.Booking;
import com.example.hotel.entity.Room;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RoomSpecification {

    public Specification<Room> getRooms(RoomFilterRequestDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }
            if (filter.getTitle() != null) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
            }
            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }
            if (filter.getMaxPeople() != null) {
                predicates.add(criteriaBuilder.equal(root.get("maxPeople"), filter.getMaxPeople()));
            }
            if (filter.getHotelId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("hotel").get("id"), filter.getHotelId()));
            }

            if (filter.getCheckInDate() != null && filter.getCheckOutDate() != null) {
                // Создаем подзапрос для проверки пересечения дат
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Booking> bookingRoot = subquery.from(Booking.class);

                // Условие для пересечения дат
                Predicate dateOverlap = criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(bookingRoot.get("checkInDate"), filter.getCheckOutDate()),
                        criteriaBuilder.greaterThanOrEqualTo(bookingRoot.get("checkOutDate"), filter.getCheckInDate())
                );

                // Связываем комнату с бронированием
                Predicate roomMatch = criteriaBuilder.equal(bookingRoot.get("room").get("id"), root.get("id"));

                // Фильтруем только те комнаты, у которых нет пересечений с бронированиями
                subquery.select(bookingRoot.get("room").get("id"))
                        .where(criteriaBuilder.and(dateOverlap, roomMatch));

                // Добавляем условие, что комната не должна быть в результатах подзапроса
                predicates.add(criteriaBuilder.not(criteriaBuilder.exists(subquery)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}