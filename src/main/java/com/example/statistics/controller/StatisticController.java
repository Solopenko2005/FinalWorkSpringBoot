package com.example.statistics.controller;

import com.example.statistics.model.StatisticEvent;
import com.example.statistics.repository.StatisticRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    private final StatisticRepository statisticRepository;

    public StatisticController(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @GetMapping("/export")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportStatisticsToCsv(HttpServletResponse response) throws IOException {
        List<StatisticEvent> events = statisticRepository.findAll();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=statistics.csv");

        try (PrintWriter writer = response.getWriter();
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                     .withHeader("ID", "Event Type", "User ID", "Event Date", "Booking Details"))) {

            for (StatisticEvent event : events) {
                csvPrinter.printRecord(event.getId(), event.getEventType(), event.getUserId(), event.getEventDate(), event.getBookingDetails());
            }

            csvPrinter.flush();
        }
    }
}