package com.example.hotel.statistics.service;

import com.example.hotel.statistics.entity.StatisticEvent;
import com.example.hotel.statistics.repository.StatisticEventRepository;
import com.opencsv.CSVWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class CsvExportService {

    @Autowired
    private StatisticEventRepository statisticEventRepository;

    public byte[] exportStatisticsToCsv() throws Exception {
        List<StatisticEvent> events = statisticEventRepository.findAll();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        CSVWriter csvWriter = new CSVWriter(new OutputStreamWriter(outputStream));

        // Заголовки CSV
        csvWriter.writeNext(new String[]{"Event Type", "User ID", "Check-In Date", "Check-Out Date", "Timestamp"});

        // Данные
        for (StatisticEvent event : events) {
            csvWriter.writeNext(new String[]{
                    event.getEventType(),
                    event.getUserId().toString(),
                    event.getCheckInDate() != null ? event.getCheckInDate().toString() : "",
                    event.getCheckOutDate() != null ? event.getCheckOutDate().toString() : "",
                    event.getTimestamp().toString()
            });
        }

        csvWriter.close();
        return outputStream.toByteArray();
    }
}