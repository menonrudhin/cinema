package com.reserveme.cinema.controller;

import com.mongodb.DuplicateKeyException;
import com.reserveme.cinema.error.ErrorUtils;
import com.reserveme.cinema.mapper.BookingDetailsMapper;
import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.model.BookingRequest;
import com.reserveme.cinema.repository.BookingDetailsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class FilmscaBookingController implements BookingController {

    @Autowired
    private ErrorUtils errorUtils;

    @Autowired
    private BookingDetailsMapper bookingDetailsMapper;

    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;

    @Override
    @PostMapping("/bookings")
    public Mono<BookingDetails> performBooking(@Valid @RequestBody BookingRequest bookingRequest, ServerWebExchange exchange) {
        BookingDetails bookingDetails = bookingDetailsMapper.mapBookingDetails(bookingRequest);
        bookingDetails.setBookingId(bookingDetails.getAuditoriumId()+bookingDetails.getShowId()+bookingDetails.getSeatNumber());

        String bookingId = bookingDetails.getBookingId();

        return bookingDetailsRepository.save(bookingDetails)
                .onErrorResume(org.springframework.dao.DuplicateKeyException.class,
                        e -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "Booking already exists, " + bookingId)));
    }
}
