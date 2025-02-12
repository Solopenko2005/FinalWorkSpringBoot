package com.example.statistics.repository;

import com.example.statistics.model.StatisticEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticRepository extends MongoRepository<StatisticEvent, String> {
}