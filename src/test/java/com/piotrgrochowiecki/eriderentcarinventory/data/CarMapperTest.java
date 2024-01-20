package com.piotrgrochowiecki.eriderentcarinventory.data;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CarMapperTest {

    private final CarMapper carMapper;

    @Autowired
    public CarMapperTest(CarMapper carMapper) {
        this.carMapper = carMapper;
    }

    @Test
    void mapToModel() {
        //given
        String carUuid = UUID.randomUUID().toString();

        CarEntity carEntity = CarEntity.builder()
                .id(1L)
                .uuid(carUuid)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("PY 42644")
                .build();

        Car car = Car.builder()
                .id(1L)
                .uuid(carUuid)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("PY 42644")
                .build();

        //when
        Car result = carMapper.mapToModel(carEntity);

        //then
        assertEquals(car, result);
    }

    @Test
    void mapToEntity() {
        //given
        Car car = Car.builder()
                .brand("Tesla")
                .model("Model X")
                .plateNumber("PY 42644")
                .build();

        CarEntity carEntity = CarEntity.builder()
                .brand("Tesla")
                .model("Model X")
                .plateNumber("PY 42644")
                .build();

        //when
        CarEntity result = carMapper.mapToEntity(car);

        //then
        assertEquals(carEntity, result);
    }

}