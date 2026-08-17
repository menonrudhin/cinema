package com.reserveme.cinema.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookingDetails")
@Data
public class BookingDetails {
    @Id
    private String bookingId;
    private String theaterName;
    private String auditoriumId;
    private int seatNumber;
    private String accountId;
    private String showId;
}
