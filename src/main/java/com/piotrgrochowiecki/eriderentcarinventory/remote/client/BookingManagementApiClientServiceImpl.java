package com.piotrgrochowiecki.eriderentcarinventory.remote.client;

import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.mapper.ApiMapper;
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
        BookingResponseDto[] bookingResponseDtos = restTemplate.getForObject(url, BookingResponseDto[].class); //TODO sprawdzić metodę exchange (pozwoli na pobranie listy)
        return Arrays.stream(bookingResponseDtos).toList().stream()
                .map(apiMapper::mapToModel)
                .collect(Collectors.toList());
    }
    //TODO poprawić url, bo zmieniono daty na query param, użyć WebClienta
    

    //TODO obsłużyć błąd 500 i inne (pomyśleć co aplikacja może innego wysłać) i dodać logowanie np.logback.
    // W mikroserwisach często korzysta się z zewnętrzych usług logujących, np. Cloud Watch na AWS
    // złapać za pomocą try-catch, zalogować oraz zwrócić swój wyjątek np. RestException
    // można też spróbować wysłać żądanie ponownie
}
