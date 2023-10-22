package com.piotrgrochowiecki.eriderentcarinventory.api.mapper;

import com.piotrgrochowiecki.eriderentcarinventory.api.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.api.dto.CarDto;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import org.springframework.stereotype.Component;

@Component
public class ApiMapper {

    public Car mapToModel(CarDto carDto) {
        return Car.builder()
                .uuid(carDto.uuid())
                .brand(carDto.brand())
                .model(carDto.model())
                .plateNumber(carDto.planeNumber())
                .build();
    }

    public Booking mapToModel(BookingResponseDto bookingResponseDto) {
        return Booking.builder()
                .id(bookingResponseDto.id())
                .startDate(bookingResponseDto.startDate())
                .endDate(bookingResponseDto.endDate())
                .userUuid(bookingResponseDto.userUuid())
                .carUuid(bookingResponseDto.carUuid())
                .build();
    }

    public CarDto mapToDto(Car car) {
        return CarDto.builder()
                .uuid(car.uuid())
                .brand(car.brand())
                .model(car.model())
                .planeNumber(car.plateNumber())
                .build();
    }

    public BookingResponseDto mapToDto(Booking booking) {
        return BookingResponseDto.builder()
                .id(booking.id())
                .startDate(booking.startDate())
                .endDate(booking.endDate())
                .userUuid(booking.userUuid())
                .carUuid(booking.carUuid())
                .build();
    }

}
