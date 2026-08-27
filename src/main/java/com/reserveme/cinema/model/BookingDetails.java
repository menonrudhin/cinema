package com.reserveme.cinema.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookingDetails")
@CompoundIndex(name = "auditorium_show_seat_idx", def = "{ 'auditoriumId': 1, 'showId': 1, 'seatNumber': 1 }", unique = true)
@Data
@EqualsAndHashCode
@ToString
public class BookingDetails {
    @Id
    private String bookingId;
    private String theaterName;
    private String auditoriumId;
    private int seatNumber;
    private String accountId;
    private String showId;
    private String email;
}
