package com.piotrgrochowiecki.eriderentcarinventory.remote.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.mapper.BookingApiMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BookingManagementApiClientServiceImplIntegrationTest {

    public static MockWebServer mockBackEnd;
    private final BookingManagementApiClientServiceImpl bookingManagementApiClientService;
    @MockBean
    private BookingApiMapper bookingApiMapper;
    private final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    public BookingManagementApiClientServiceImplIntegrationTest(BookingManagementApiClientServiceImpl bookingManagementApiClientService, BookingApiMapper bookingApiMapper) {
        this.bookingManagementApiClientService = bookingManagementApiClientService;
        this.bookingApiMapper = bookingApiMapper;
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start(8083);
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @Test
    void shouldReturnListOfBookings() throws IOException, InterruptedException {
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

        List<Booking> expectedResponseList = List.of(booking1, booking2, booking3);

        mockBackEnd.enqueue(new MockResponse().setBody(MAPPER.registerModule(new JavaTimeModule())
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                        .writerWithDefaultPrettyPrinter()
                        .writeValueAsString(responseListMock))
                .addHeader("Content-Type", "application/json"));

        List<Booking> resultList = bookingManagementApiClientService.getAllBookingsOverlappingWithDates(startDate, endDate);

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(expectedResponseList, resultList);

    }

}