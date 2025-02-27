package com.exam.set.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantReservation {

    private String contactName;
    private String contactNumber;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer partySize;

}
