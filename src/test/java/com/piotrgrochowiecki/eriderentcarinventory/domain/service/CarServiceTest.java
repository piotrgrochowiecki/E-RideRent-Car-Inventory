package com.piotrgrochowiecki.eriderentcarinventory.domain.service;

import com.piotrgrochowiecki.eriderentcarinventory.domain.client.BookingManagementApiClientService;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private BookingManagementApiClientService bookingManagementApiClientService;

    @Autowired
    private CarService carService;

    @BeforeEach
    void setUp() {
        Car car1 = Car.builder()
                .id(1L)
                .uuid("uuid1")
                .brand("Tesla")
                .model("Cybertruck")
                .plateNumber("WX 1234")
                .build();

        Car car2 = Car.builder()
                .id(2L)
                .uuid("uuid2")
                .brand("Tesla")
                .model("X")
                .plateNumber("WK 5264")
                .build();

        Car car3 = Car.builder()
                .id(3L)
                .uuid("uuid3")
                .brand("Mercedes")
                .model("EQC")
                .plateNumber("WP 8564")
                .build();

        Car car4 = Car.builder()
                .id(4L)
                .uuid("uuid4")
                .brand("VW")
                .model("ID.4")
                .plateNumber("WPI 5433")
                .build();

        List<Car> allCars = List.of(car1, car2, car3, car4);

        when(carRepository.findAll()).thenReturn(allCars);
        when(carRepository.findByUuid("uuid1")).thenReturn(Optional.of(car1));
        when(carRepository.findByUuid("uuid2")).thenReturn(Optional.of(car2));
        when(carRepository.findByUuid("uuid3")).thenReturn(Optional.of(car3));
        when(carRepository.findByUuid("uuid4")).thenReturn(Optional.of(car4));

        Booking booking1 = Booking.builder()
                .id(1L)
                .startDate(LocalDate.of(2023, 10, 1))
                .endDate(LocalDate.of(2023, 10, 15))
                .carUuid(car1.uuid())
                .userUuid(UUID.randomUUID().toString())
                .build();

        Booking booking2 = Booking.builder()
                .id(2L)
                .startDate(LocalDate.of(2023, 10, 1))
                .endDate(LocalDate.of(2023, 10, 5))
                .carUuid(car2.uuid())
                .userUuid(UUID.randomUUID().toString())
                .build();

        Booking booking3 = Booking.builder()
                .id(3L)
                .startDate(LocalDate.of(2023, 10, 6))
                .endDate(LocalDate.of(2023, 10, 15))
                .carUuid(car3.uuid())
                .userUuid(UUID.randomUUID().toString())
                .build();

        Booking booking4 = Booking.builder()
                .id(4L)
                .startDate(LocalDate.of(2023, 11, 10))
                .endDate(LocalDate.of(2023, 11, 15))
                .carUuid(car4.uuid())
                .userUuid(UUID.randomUUID().toString())
                .build();

        //1st test
        when(bookingManagementApiClientService.getAllBookingsOverlappingWithDates(LocalDate.of(2023, 10, 1),
                                                                                  LocalDate.of(2023, 10, 17)))
                .thenReturn(List.of(booking1, booking2, booking3));

        //2nd test
        when(bookingManagementApiClientService.getAllBookingsOverlappingWithDates(LocalDate.of(2023, 10, 1),
                                                                                  LocalDate.of(2023, 11, 20)))
                .thenReturn(List.of(booking1, booking2, booking3, booking4));

        //3rd test
        when(bookingManagementApiClientService.getAllBookingsOverlappingWithDates(LocalDate.of(2023, 11, 17),
                                                                                  LocalDate.of(2023, 11, 22)))
                .thenReturn(List.of());

    }

    @Test
    @DisplayName("Given 3 bookings with different cars in requested period and 4 cars available in total" +
                 "when getAvailableCars method is invoked" +
                 "it should return list with one car")
    void shouldReturnListWithOneCar() {
        //given
        Car car4 = Car.builder()
                .id(4L)
                .uuid("uuid4")
                .brand("VW")
                .model("ID.4")
                .plateNumber("WPI 5433")
                .build();

        //when
        List<Car> result = carService.getAvailableCars(LocalDate.of(2023, 10, 1),
                                                        LocalDate.of(2023, 10, 17));

        //then
        assertEquals(1, result.size());
        assertEquals(result.get(0), car4);
    }

    @Test
    @DisplayName("Given 4 bookings with different cars in requested period and 4 cars available in total" +
                 "when getAvailableCars method is invoked" +
                 "it should return list with no cars")
    void shouldReturnListWithNoCars() {
        //given

        //when
        List<Car> result = carService.getAvailableCars(LocalDate.of(2023, 10, 1),
                                                       LocalDate.of(2023, 11, 20));

        //then
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Given no bookings in requested period and 4 cars available in total" +
                 "when getAvailableCars method is invoked" +
                 "it should return list with 4 cars")
    void shouldReturnListWithFourCars() {
        //given
        Car car1 = Car.builder()
                .id(1L)
                .uuid("uuid1")
                .brand("Tesla")
                .model("Cybertruck")
                .plateNumber("WX 1234")
                .build();

        Car car2 = Car.builder()
                .id(2L)
                .uuid("uuid2")
                .brand("Tesla")
                .model("X")
                .plateNumber("WK 5264")
                .build();

        Car car3 = Car.builder()
                .id(3L)
                .uuid("uuid3")
                .brand("Mercedes")
                .model("EQC")
                .plateNumber("WP 8564")
                .build();

        Car car4 = Car.builder()
                .id(4L)
                .uuid("uuid4")
                .brand("VW")
                .model("ID.4")
                .plateNumber("WPI 5433")
                .build();

        //when
        List<Car> result = carService.getAvailableCars(LocalDate.of(2023, 11, 17),
                                                       LocalDate.of(2023, 11, 22));

        //then
        assertEquals(4, result.size());
        assertTrue(result.contains(car1));
        assertTrue(result.contains(car2));
        assertTrue(result.contains(car3));
        assertTrue(result.contains(car4));
    }

}