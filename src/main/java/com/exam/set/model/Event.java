package com.exam.set.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

/**
 * Class representing an event (arrival or departure) in the timeline.
 */
@Getter
@Setter
@AllArgsConstructor
public class Event {

    private LocalTime time;
    private Integer tableChange;    // Positive for arrival, negative for departure.

}
