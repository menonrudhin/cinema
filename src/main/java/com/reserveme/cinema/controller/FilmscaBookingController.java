package com.reserveme.cinema.controller;

import com.mongodb.DuplicateKeyException;
import com.reserveme.cinema.error.ErrorUtils;
import com.reserveme.cinema.mapper.BookingDetailsMapper;
import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.model.BookingRequest;
import com.reserveme.cinema.repository.BookingDetailsRepository;
import com.reserveme.cinema.repository.ShowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    private ShowsRepository showsRepository;

    private final String unavailableMessage = "the seat is unavailable, please try other seats";
    private final String showNotFoundMessage = "the show is not found, please check the show id";

    @Override
    @PostMapping("/bookings")
    public Mono<ResponseEntity<BookingDetails>> performBooking(@Validated @RequestBody BookingRequest bookingRequest, ServerWebExchange exchange) {
        BookingDetails bookingDetails = bookingDetailsMapper.mapBookingDetails(bookingRequest);
        bookingDetails.setBookingId(bookingDetails.getAuditoriumId()+bookingDetails.getShowId()+bookingDetails.getSeatNumber());

        return  showsRepository.findByShowId(bookingDetails.getShowId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, showNotFoundMessage)))
                .then(
                    bookingDetailsRepository.findByAuditoriumIdAndSeatNumberAndShowId(
                            bookingDetails.getAuditoriumId(), bookingDetails.getSeatNumber(), bookingDetails.getShowId())
                    .next()
                    .flatMap(existingBooking -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                            .header("X-Message", unavailableMessage)
                            .body(existingBooking)))
                    .switchIfEmpty(
                            bookingDetailsRepository.save(bookingDetails)
                                    .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved))
                                    .onErrorResume(DuplicateKeyException.class, e -> Mono.just(ResponseEntity.status(HttpStatus.CONFLICT)
                                            .header("X-Message", unavailableMessage)
                                            .build()))
                    )
                );
    }

    @Override
    @PostMapping("/admissionRequest/{bookingId}")
    public Mono<ResponseEntity<Boolean>> performAdmission(@Validated @PathVariable String bookingId, ServerWebExchange exchange) {
        return bookingDetailsRepository.findByBookingIdAndAdmissionFlag(bookingId, false)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Ticket cannot be re-used!")))
                .flatMap(existingBooking -> {
                    existingBooking.setAdmissionFlag(true);
                    return bookingDetailsRepository.save(existingBooking)
                            .onErrorResume(e -> Mono.error(new ResponseStatusException(HttpStatus.CONFLICT, "System error: " + e.getMessage())))
                            .flatMap(booking -> Mono.just(ResponseEntity.status(HttpStatus.ACCEPTED).build()));
                 });
    }
}
