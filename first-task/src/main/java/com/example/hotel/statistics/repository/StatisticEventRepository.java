package com.example.hotel.statistics.repository;

import com.example.hotel.statistics.entity.StatisticEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticEventRepository extends MongoRepository<StatisticEvent, String> {
}