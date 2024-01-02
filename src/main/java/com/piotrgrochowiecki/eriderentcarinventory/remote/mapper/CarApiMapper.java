package com.piotrgrochowiecki.eriderentcarinventory.remote.mapper;

import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreateRequestDto;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreatedResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CarApiMapper {

    public Car mapToModel(CarCreateRequestDto carCreateRequestDto) {
        return Car.builder()
                .brand(carCreateRequestDto.brand())
                .model(carCreateRequestDto.model())
                .plateNumber(carCreateRequestDto.plateNumber())
                .build();
    }

    public CarCreatedResponseDto mapToCarCreatedResponseDto(Car car) {
        return CarCreatedResponseDto.builder()
                .brand(car.brand())
                .model(car.model())
                .plateNumber(car.plateNumber())
                .build();
    }

    public CarResponseDto mapToCarResponseDto(Car car) {
        return CarResponseDto.builder()
                .id(car.id())
                .uuid(car.uuid())
                .brand(car.brand())
                .model(car.model())
                .plateNumber(car.plateNumber())
                .build();
    }

}
