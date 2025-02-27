package com.exam.set.service;

import com.exam.set.model.Event;
import com.exam.set.model.RestaurantReservation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookRestaurantService {

    /**
     * Calculates the minimum number of tables required for the given target date based on reservation data.
     *
     * @param targetDate   The date for which to calculate table requirements.
     * @param reservations A list of reservations.
     * @return The minimum number of tables needed.
     */
    public int calculateRequiredTables(LocalDate targetDate, List<RestaurantReservation> reservations) {
        List<Event> events = new ArrayList<>();

        // Filter reservations that match the target date.
        for (RestaurantReservation res : reservations) {
            if (res.getDate().equals(targetDate)) {
                // Calculate the number of tables needed for this reservation.
                // Each table seats 4, so use ceiling division.
                int tablesNeeded = (res.getPartySize() + 3) / 4; // For example, 5 people require 2 tables.

                // Create events for arrival and departure.
                events.add(new Event(res.getStartTime(), tablesNeeded));
                events.add(new Event(res.getEndTime(), -tablesNeeded));
            }
        }

        // Sort events by time.
        // If times are equal, process departures before arrivals to free tables immediately.
        events.sort((e1, e2) -> {
            if (e1.getTime().equals(e2.getTime())) {
                return Integer.compare(e1.getTableChange(), e2.getTableChange());
            }
            return e1.getTime().compareTo(e2.getTime());
        });

        int currentTables = 0;
        int maxTables = 0;

        // Process events to determine the maximum number of simultaneous tables needed.
        for (Event event : events) {
            currentTables += event.getTableChange();
            maxTables = Math.max(maxTables, currentTables);
        }
        return maxTables;
    }

}
