package com.piotrgrochowiecki.eriderentcarinventory.remote.client;

import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.domain.client.BookingManagementApiClientService;
import com.piotrgrochowiecki.eriderentcarinventory.remote.mapper.BookingApiMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingManagementApiClientServiceImpl implements BookingManagementApiClientService {

    private final WebClient webClient;
    private final BookingApiMapper bookingApiMapper;

    @Value("${url.bookingManagement}")
    private String BOOKING_MANAGEMENT_HOST;

    @Value("${url.bookingManagement.booking}")
    private String BOOKING_MANAGEMENT;

    @Value("${url.bookingManagement.booking.overlapping}")
    private String BOOKING_MANAGEMENT_OVERLAPPING;

    @Override
    public List<Booking> getAllBookingsOverlappingWithDates(LocalDate newBookingStartDate, LocalDate newBookingEndDate) {
        List<BookingResponseDto> responseList = webClient.get()
                .uri(BOOKING_MANAGEMENT_HOST + BOOKING_MANAGEMENT + BOOKING_MANAGEMENT_OVERLAPPING + newBookingEndDate + "/" + newBookingEndDate)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<BookingResponseDto>>() {})
                .block();
        if(responseList == null) {
            throw new RuntimeException("Did not receive response from Booking Service");
        }
        return responseList.stream()
                .map(bookingApiMapper::mapToModel)
                .collect(Collectors.toList());
    }

}
