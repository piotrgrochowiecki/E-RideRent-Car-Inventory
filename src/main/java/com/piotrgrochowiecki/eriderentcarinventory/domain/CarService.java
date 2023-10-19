package com.piotrgrochowiecki.eriderentcarinventory.domain;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Car add(@Nullable Car car) {
        assert car != null;
        if (doesCarAlreadyExists(car.plateNumber())) {
            throw new CarAlreadyExistsRuntimeException(car.plateNumber());
        }
        return carRepository.save(car);
    }

    public Car getById(@Nullable Long id) {
        assert id != null;
        if(carRepository.findById(id).isEmpty()) {
            throw new NotFoundRuntimeException(id.toString());
        }
        return carRepository.findById(id).get();
    }

    public Car getByUuid(@Nullable String uuid) {
        assert uuid != null;
        if(carRepository.findByUuid(uuid).isEmpty()) {
            throw new NotFoundRuntimeException(uuid);
        }
        return carRepository.findByUuid(uuid).get();
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    private boolean doesCarAlreadyExists(String plateNumber) {
        return carRepository.findByPlateNumber(plateNumber).isPresent();
    }

}
