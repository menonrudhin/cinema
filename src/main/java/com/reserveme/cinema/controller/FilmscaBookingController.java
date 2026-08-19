package com.reserveme.cinema.controller;

import com.reserveme.cinema.mapper.BookingDetailsMapper;
import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.model.BookingRequest;
import com.reserveme.cinema.repository.BookingDetailsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FilmscaBookingController implements BookingController {

    @Autowired
    private BookingDetailsMapper bookingDetailsMapper;

    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;

    @Override
    @PostMapping("/bookings")
    public Mono<BookingDetails> performBooking(@Valid @RequestBody BookingRequest bookingRequest) {
        BookingDetails bookingDetails = bookingDetailsMapper.mapBookingDetails(bookingRequest);
        return bookingDetailsRepository.save(bookingDetails);
    }
}
