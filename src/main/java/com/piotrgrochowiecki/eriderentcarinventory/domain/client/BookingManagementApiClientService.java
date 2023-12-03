package com.piotrgrochowiecki.eriderentcarinventory.domain.client;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingManagementApiClientService {

    List<Booking> getAllBookingsOverlappingWithDates(LocalDate newBookingStartDate, LocalDate newBookingEndDate);

}
