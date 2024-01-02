package com.piotrgrochowiecki.eriderentcarinventory.domain.repository;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    Car save(Car car);

    Optional<Car> findById(Long id);

    Optional<Car> findByUuid(String uuid);

    List<Car> findAll();

    Page<Car> findAll(Pageable pageable);

    boolean existsByPlateNumber(String plateNumber);

}
