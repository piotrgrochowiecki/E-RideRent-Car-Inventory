package com.piotrgrochowiecki.eriderentcarinventory.api.client;

import com.piotrgrochowiecki.eriderentcarinventory.api.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.api.mapper.ApiMapper;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.domain.client.BookingManagementApiClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingManagementApiClientServiceImpl implements BookingManagementApiClientService {

    private final RestTemplate restTemplate;
    private final ApiMapper apiMapper;

    @Override
    public List<Booking> getAllBookingsOverlappingWithDates(LocalDate newBookingStartDate, LocalDate newBookingEndDate) {
        String url = BOOKING_MANAGEMENT_API_ENDPOINT + "all-overlapping-with-dates/" + newBookingStartDate + "/" + newBookingEndDate;
        BookingResponseDto[] bookingResponseDtos = restTemplate.getForObject(url, BookingResponseDto[].class);
        assert bookingResponseDtos != null;
        return Arrays.stream(bookingResponseDtos).toList().stream()
                .map(apiMapper::mapToModel)
                .collect(Collectors.toList());
    }

}
