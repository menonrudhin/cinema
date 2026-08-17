package com.reserveme.cinema.repository;

import com.reserveme.cinema.model.BookingDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface BookingDetailsRepository extends ReactiveMongoRepository<BookingDetails, String> {
    Flux<BookingDetails> findByBookingId(String bookingId);
}
