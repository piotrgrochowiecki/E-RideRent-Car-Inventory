package com.piotrgrochowiecki.eriderentcarinventory.remote.mapper;

import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreateRequestDto;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarApiMapper {

    public Car mapToModel(CarCreateRequestDto carCreateRequestDto) {
        return Car.builder()
                .uuid(carCreateRequestDto.uuid())
                .brand(carCreateRequestDto.brand())
                .model(carCreateRequestDto.model())
                .plateNumber(carCreateRequestDto.planeNumber())
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

}
