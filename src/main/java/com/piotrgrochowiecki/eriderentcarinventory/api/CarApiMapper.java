package com.piotrgrochowiecki.eriderentcarinventory.api;

import com.piotrgrochowiecki.eriderentcarinventory.domain.Car;
import org.springframework.stereotype.Component;

@Component
public class CarApiMapper {

    public Car mapToModel(CarDto carDto) {
        return Car.builder()
                .uuid(carDto.uuid())
                .brand(carDto.brand())
                .model(carDto.model())
                .plateNumber(carDto.planeNumber())
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

}
