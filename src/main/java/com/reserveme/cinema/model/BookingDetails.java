package com.reserveme.cinema.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookingDetails")
@Data
@EqualsAndHashCode
public class BookingDetails {
    @Id
    private String bookingId;
    private String theaterName;
    @Indexed(unique = true)
    private String auditoriumId;
    @Indexed(unique = true)
    private int seatNumber;
    private String accountId;
    @Indexed(unique = true)
    private String showId;
    private String email;
}
