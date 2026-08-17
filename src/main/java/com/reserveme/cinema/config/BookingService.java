package com.reserveme.cinema.config;

import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.model.BookingRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface BookingService {
    public Mono<BookingDetails> createBooking(BookingRequest bookingRequest);
}
