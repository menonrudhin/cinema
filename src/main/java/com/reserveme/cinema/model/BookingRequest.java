package com.reserveme.cinema.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    @NotBlank(message = "Theater name cannot be empty")
    private String theaterName;
    @NotBlank(message = "Auditorium ID cannot be empty")
    @Positive(message = "Auditorium ID must be a positive number")
    private String auditoriumId;
    @NotBlank(message = "Seat number cannot be empty")
    @Positive(message = "Seat number must be a positive number")
    private int seatNumber;
    @NotBlank(message = "Account ID cannot be empty")
    @Positive(message = "Account ID must be a positive number")
    private String accountId;
    @NotBlank(message = "Show ID cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Show ID must be alphanumeric")
    private String showId;
    @NotBlank
    @Email(message = "Email should be valid")
    private String email;
}
