package com.piotrgrochowiecki.eriderentcarinventory.remote.mapper;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.BookingResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BookingApiMapper {

    public Booking mapToModel(BookingResponseDto bookingResponseDto) {
        return Booking.builder()
                .id(bookingResponseDto.id())
                .startDate(bookingResponseDto.startDate())
                .endDate(bookingResponseDto.endDate())
                .userUuid(bookingResponseDto.userUuid())
                .carUuid(bookingResponseDto.carUuid())
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
