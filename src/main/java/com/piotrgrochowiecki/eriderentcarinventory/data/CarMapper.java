package com.piotrgrochowiecki.eriderentcarinventory.data;

import com.piotrgrochowiecki.eriderentcarinventory.domain.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car mapToModel(CarEntity carEntity) {
        return Car.builder()
                .id(carEntity.getId())
                .uuid(carEntity.getUuid())
                .brand(carEntity.getBrand())
                .model(carEntity.getModel())
                .plateNumber(carEntity.getPlateNumber())
                .build();
    }

    public CarEntity mapToEntity(Car car) {
        return CarEntity.builder()
                .brand(car.brand())
                .model(car.model())
                .plateNumber(car.plateNumber())
                .build();
    }

}
