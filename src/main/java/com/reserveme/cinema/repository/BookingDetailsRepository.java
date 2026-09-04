package com.reserveme.cinema.repository;

import com.reserveme.cinema.model.BookingDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookingDetailsRepository extends ReactiveMongoRepository<BookingDetails, String> {
    Flux<BookingDetails> findByBookingId(String bookingId);
    Flux<BookingDetails> findByAuditoriumIdAndSeatNumberAndShowId(String auditoriumId, int seatNumber, String showId);
    Mono<BookingDetails> findByBookingIdAndAdmissionFlag(String bookingId, boolean admissionFlag);
}
