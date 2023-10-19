package com.piotrgrochowiecki.eriderentcarinventory.domain;

import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Car save(Car car);

    Optional<Car> findById(@Nullable Long id);

    Optional<Car> findByUuid(@Nullable String uuid);

    Optional<Car> findByPlateNumber(@Nullable String plateNumber);

    List<Car> findAll();

}
