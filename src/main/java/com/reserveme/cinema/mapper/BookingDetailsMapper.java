package com.reserveme.cinema.mapper;

import com.reserveme.cinema.model.BookingDetails;
import com.reserveme.cinema.model.BookingRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingDetailsMapper {

    @Mapping(source="theaterName", target="theaterName")
    @Mapping(source="auditoriumId", target="auditoriumId")
    @Mapping(source="seatNumber", target="seatNumber")
    @Mapping(source="accountId", target="accountId")
    @Mapping(source="showId", target="showId")
    @Mapping(source = "email", target = "email")
    BookingDetails mapBookingDetails(BookingRequest bookingRequest);
}
