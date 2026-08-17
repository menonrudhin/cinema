package com.reserveme.cinema;

import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.repository.BookingDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class BookingDetailsRepositoryTests {

    @Autowired
    private BookingDetailsRepository repository;

    @Autowired
    private Environment environment;

    @BeforeEach
    void setUp() {
        System.out.println("ACTIVE PROFILES: "
                + Arrays.toString(environment.getActiveProfiles()));

        System.out.println("MONGO URI: "
                + environment.getProperty("spring.data.mongodb.uri"));

        System.out.println("MONGO DATABASE: "
                + environment.getProperty("spring.data.mongodb.database"));

        repository.deleteAll().block();
    }

    @Test
    void shouldCreateAndReadBooking() {
        BookingDetails booking = createBooking("booking-1", "Grand Theater", "A1", 12, "acct-1", "show-1");

        StepVerifier.create(repository.save(booking)
                .flatMap(saved -> repository.findById(saved.getBookingId())))
                .assertNext(found -> {
                    assertThat(found.getBookingId()).isEqualTo("booking-1");
                    assertThat(found.getTheaterName()).isEqualTo("Grand Theater");
                    assertThat(found.getAuditoriumId()).isEqualTo("A1");
                    assertThat(found.getSeatNumber()).isEqualTo(12);
                    assertThat(found.getAccountId()).isEqualTo("acct-1");
                    assertThat(found.getShowId()).isEqualTo("show-1");
                })
                .verifyComplete();
    }

    @Test
    void shouldFindBookingByBookingId() {
        BookingDetails booking = createBooking("booking-2", "Skyline Cinema", "B3", 22, "acct-2", "show-2");

        StepVerifier.create(repository.save(booking)
                .thenMany(repository.findByBookingId("booking-2")))
                .assertNext(found -> {
                    assertThat(found.getBookingId()).isEqualTo("booking-2");
                    assertThat(found.getTheaterName()).isEqualTo("Skyline Cinema");
                    assertThat(found.getSeatNumber()).isEqualTo(22);
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateBooking() {
        BookingDetails booking = createBooking("booking-3", "Downtown Cinema", "C5", 8, "acct-3", "show-3");

        StepVerifier.create(repository.save(booking)
                .flatMap(saved -> {
                    saved.setSeatNumber(15);
                    saved.setTheaterName("Downtown Deluxe");
                    return repository.save(saved);
                })
                .flatMap(saved -> repository.findById(saved.getBookingId())))
                .assertNext(updated -> {
                    assertThat(updated.getSeatNumber()).isEqualTo(15);
                    assertThat(updated.getTheaterName()).isEqualTo("Downtown Deluxe");
                })
                .verifyComplete();
    }

    @Test
    void shouldDeleteBooking() {
        BookingDetails booking = createBooking("booking-4", "Riverside Cinema", "D2", 30, "acct-4", "show-4");

        StepVerifier.create(repository.save(booking)
                .flatMap(saved -> repository.deleteById(saved.getBookingId())
                        .then(repository.findById(saved.getBookingId()))))
                .verifyComplete();
    }

    private BookingDetails createBooking(String bookingId, String theaterName, String auditoriumId,
                                        int seatNumber, String accountId, String showId) {
        BookingDetails booking = new BookingDetails();
        booking.setBookingId(bookingId);
        booking.setTheaterName(theaterName);
        booking.setAuditoriumId(auditoriumId);
        booking.setSeatNumber(seatNumber);
        booking.setAccountId(accountId);
        booking.setShowId(showId);
        return booking;
    }
}
