package com.exam.set.service;

import com.exam.set.model.RestaurantReservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookRestaurantServiceTest {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @InjectMocks
    private BookRestaurantService bookRestaurantService;

    /**
     * Case 1:
     * If two groups of 3 people reserve at the same time,
     * then 2 tables should be required.
     */
    @Test
    void testConcurrentReservations() {
        List<RestaurantReservation> reservations = new ArrayList<>();
        LocalDate targetDate = LocalDate.parse("2025-03-01", dateFormatter);

        // Group 1: 3 people from 12:00 to 13:00.
        reservations.add(new RestaurantReservation(
                "Group 1", "0123456789",
                targetDate,
                LocalTime.parse("12:00", timeFormatter),
                LocalTime.parse("13:00", timeFormatter),
                3));

        // Group 2: 3 people from 12:00 to 13:00.
        reservations.add(new RestaurantReservation(
                "Group 2", "0987654321",
                targetDate,
                LocalTime.parse("12:00", timeFormatter),
                LocalTime.parse("13:00", timeFormatter),
                3));

        int requiredTables = bookRestaurantService.calculateRequiredTables(targetDate, reservations);
        // Expected: 2 tables because both groups overlap.
        assertEquals(2, requiredTables);
    }

    /**
     * Case 2:
     * If two groups reserve at different times,
     * then only 1 table is required as the same table can be reused.
     */
    @Test
    void testNonOverlappingReservations() {
        List<RestaurantReservation> reservations = new ArrayList<>();
        LocalDate targetDate = LocalDate.parse("2025-03-01", dateFormatter);

        // Group 1: 3 people from 12:00 to 13:00.
        reservations.add(new RestaurantReservation(
                "Group 1", "0123456789",
                targetDate,
                LocalTime.parse("12:00", timeFormatter),
                LocalTime.parse("13:00", timeFormatter),
                3));

        // Group 2: 3 people from 13:00 to 14:00 (non-overlapping).
        reservations.add(new RestaurantReservation(
                "Group 2", "0987654321",
                targetDate,
                LocalTime.parse("13:00", timeFormatter),
                LocalTime.parse("14:00", timeFormatter),
                3));

        int requiredTables = bookRestaurantService.calculateRequiredTables(targetDate, reservations);
        // Expected: 1 table since there is no overlap.
        assertEquals(1, requiredTables);
    }
}
