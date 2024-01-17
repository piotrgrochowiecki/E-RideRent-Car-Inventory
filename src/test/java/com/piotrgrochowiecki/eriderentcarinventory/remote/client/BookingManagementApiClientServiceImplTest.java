package com.piotrgrochowiecki.eriderentcarinventory.remote.client;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.mapper.BookingApiMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookingManagementApiClientServiceImplTest {

    @MockBean
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;

    @Mock
    private WebClient.ResponseSpec responseMock;

    @MockBean
    private BookingApiMapper bookingApiMapper;

    private final BookingManagementApiClientServiceImpl bookingManagementApiClientService;

    @Autowired
    public BookingManagementApiClientServiceImplTest(WebClient webClientMock, BookingApiMapper bookingApiMapper, BookingManagementApiClientServiceImpl bookingManagementApiClientService) {
        this.webClientMock = webClientMock;
        this.bookingApiMapper = bookingApiMapper;
        this.bookingManagementApiClientService = bookingManagementApiClientService;
    }

    @Test
    void shouldReturnListOfBookings() {
        //given
        LocalDate startDate = LocalDate.of(2023, 10, 18);
        LocalDate endDate = LocalDate.of(2023, 11, 5);

        BookingResponseDto bookingResponseDto1 = BookingResponseDto.builder()
                .id(1L)
                .carUuid("carUuid1")
                .userUuid("userUuid1")
                .startDate(LocalDate.of(2023, 10, 10))
                .endDate(LocalDate.of(2023, 10, 17))
                .build();

        BookingResponseDto bookingResponseDto2 = BookingResponseDto.builder()
                .id(2L)
                .carUuid("carUuid2")
                .userUuid("userUuid2")
                .startDate(LocalDate.of(2023, 10, 12))
                .endDate(LocalDate.of(2023, 10, 28))
                .build();

        BookingResponseDto bookingResponseDto3 = BookingResponseDto.builder()
                .id(3L)
                .carUuid("carUuid3")
                .userUuid("userUuid3")
                .startDate(LocalDate.of(2023, 11, 7))
                .endDate(LocalDate.of(2023, 11, 14))
                .build();

        List<BookingResponseDto> responseListMock = List.of(bookingResponseDto1,
                bookingResponseDto2,
                bookingResponseDto3);

        Booking booking1 = Booking.builder()
                .id(1L)
                .carUuid("carUuid1")
                .userUuid("userUuid1")
                .startDate(LocalDate.of(2023, 10, 10))
                .endDate(LocalDate.of(2023, 10, 17))
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .carUuid("carUuid2")
                .userUuid("userUuid2")
                .startDate(LocalDate.of(2023, 10, 12))
                .endDate(LocalDate.of(2023, 10, 28))
                .build();

        Booking booking3 = Booking.builder()
                .id(3L)
                .carUuid("carUuid3")
                .userUuid("userUuid3")
                .startDate(LocalDate.of(2023, 11, 7))
                .endDate(LocalDate.of(2023, 11, 14))
                .build();

        when(bookingApiMapper.mapToModel(bookingResponseDto1))
                .thenReturn(booking1);
        when(bookingApiMapper.mapToModel(bookingResponseDto2))
                .thenReturn(booking2);
        when(bookingApiMapper.mapToModel(bookingResponseDto3))
                .thenReturn(booking3);

        when(webClientMock.get())
                .thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(anyString()))
                .thenReturn(requestHeadersMock);
        when(requestHeadersMock.accept(MediaType.APPLICATION_JSON))
                .thenReturn(requestHeadersMock);
        when(requestHeadersMock.retrieve())
                .thenReturn(responseMock);
        when(responseMock.bodyToMono(new ParameterizedTypeReference<List<BookingResponseDto>>() {}))
                .thenReturn(Mono.just(responseListMock));

        //when
        List<Booking> resultBookingList = bookingManagementApiClientService.getAllBookingsOverlappingWithDates(startDate, endDate);

        //then
        assertEquals(3, resultBookingList.size());
        assertTrue(resultBookingList.contains(booking1));
        assertTrue(resultBookingList.contains(booking2));
        assertTrue(resultBookingList.contains(booking3));
    }

}