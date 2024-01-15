package com.piotrgrochowiecki.eriderentcarinventory.remote.mapper;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreateRequestDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreatedResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarApiMapperTest {

    private CarApiMapper carApiMapper;

    @Autowired
    public CarApiMapperTest(CarApiMapper carApiMapper) {
        this.carApiMapper = carApiMapper;
    }

    private static String carUuid;

    @BeforeAll
    static void setUp() {
        carUuid = UUID.randomUUID().toString();
    }

    @Test
    @DisplayName("Given CarCreateRequestDto object, " +
            "when mapToModel, " +
            "then return Car object")
    void shouldReturnCar() {
        //given
        CarCreateRequestDto carCreateRequestDto = CarCreateRequestDto.builder()
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WK 4325")
                .build();

        Car car = Car.builder()
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WK 4325")
                .build();

        //when
        Car result = carApiMapper.mapToModel(carCreateRequestDto);

        //then
        assertEquals(car, result);
    }

    @Test
    @DisplayName("Given Car object, " +
            "when mapToCarCreatedResponseDto, " +
            "then return CarCreateRequestDto object")
    void shouldReturnCarCreatedResponseDto() {
        //given
        CarCreatedResponseDto carCreatedResponseDto = CarCreatedResponseDto.builder()
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WK 4325")
                .build();

        Car car = Car.builder()
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WK 4325")
                .build();

        //when
        CarCreatedResponseDto result = carApiMapper.mapToCarCreatedResponseDto(car);

        //then
        assertEquals(carCreatedResponseDto, result);
    }

    @Test
    @DisplayName("Given Car object, " +
            "when mapToCarResponseDto, " +
            "then return CarResponseDto object")
    void shouldReturnCarResponseDto() {
        //given
        Car car = Car.builder()
                .id(1L)
                .uuid(carUuid)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WK 4325")
                .build();

        CarResponseDto carResponseDto = CarResponseDto.builder()
                .id(1L)
                .uuid(carUuid)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WK 4325")
                .build();

        //when
        CarResponseDto result = carApiMapper.mapToCarResponseDto(car);

        //then
        assertEquals(carResponseDto, result);
    }

}