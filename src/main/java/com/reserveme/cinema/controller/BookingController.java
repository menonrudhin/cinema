package com.reserveme.cinema.controller;

import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.model.BookingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public interface BookingController {
    Mono<ResponseEntity<BookingDetails>> performBooking(@Validated @RequestBody BookingRequest bookingRequest, ServerWebExchange exchange);
    Mono<ResponseEntity<Boolean>> performAdmission(@Validated @PathVariable String bookingId, ServerWebExchange exchange);
}
