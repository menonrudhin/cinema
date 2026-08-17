package com.reserveme.cinema.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private String theaterName;
    private String auditoriumId;
    private int seatNumber;
    private String accountId;
    private String showId;
}
