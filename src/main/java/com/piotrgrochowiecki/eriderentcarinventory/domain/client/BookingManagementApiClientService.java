package com.piotrgrochowiecki.eriderentcarinventory.domain.client;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingManagementApiClientService {

    String BOOKING_MANAGEMENT_API_ENDPOINT = "http://localhost:8083/api/v1/internal/booking/";

    List<Booking> getAllBookingsOverlappingWithDates(LocalDate newBookingStartDate, LocalDate newBookingEndDate);

}
