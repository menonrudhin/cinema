package com.reserveme.cinema.controller;

import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.model.BookingRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public interface BookingController {
    Mono<BookingDetails> performBooking(@Validated @RequestBody BookingRequest bookingRequest);
}
