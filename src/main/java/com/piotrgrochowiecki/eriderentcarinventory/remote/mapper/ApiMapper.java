package com.piotrgrochowiecki.eriderentcarinventory.remote.mapper;

import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.BookingResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreateRequestDto;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import org.springframework.stereotype.Component;

@Component
public class ApiMapper {

    public Car mapToModel(CarCreateRequestDto carCreateRequestDto) {
        return Car.builder()
                .uuid(carCreateRequestDto.uuid())
                .brand(carCreateRequestDto.brand())
                .model(carCreateRequestDto.model())
                .plateNumber(carCreateRequestDto.planeNumber())
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

    public CarCreateRequestDto mapToDto(Car car) {
        return CarCreateRequestDto.builder()
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
