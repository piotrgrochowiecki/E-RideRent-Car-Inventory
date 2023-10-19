package com.piotrgrochowiecki.eriderentcarinventory.data;

import com.piotrgrochowiecki.eriderentcarinventory.domain.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    private final CarCRUDRepository carCRUDRepository;
    private final CarMapper carMapper;

    @Override
    public Car save(@Nullable Car car) {
        assert car != null;
        CarEntity carEntity = carMapper.mapToEntity(car);
        UUID uuid = UUID.randomUUID();
        carEntity.setUuid(uuid.toString());
        CarEntity savedCarEntity = carCRUDRepository.save(carEntity);
        return carMapper.mapToModel(savedCarEntity);
    }

    @Override
    public Optional<Car> findById(Long id) {
        assert id != null;
        return carCRUDRepository.findById(id)
                .map(carMapper::mapToModel);
    }

    @Override
    public Optional<Car> findByUuid(String uuid) {
        assert uuid != null;
        return carCRUDRepository.findByUuid(uuid)
                .map(carMapper::mapToModel);
    }

    @Override
    public Optional<Car> findByPlateNumber(String plateNumber) {
        assert plateNumber != null;
        return carCRUDRepository.findByPlateNumber(plateNumber)
                .map(carMapper::mapToModel);
    }

    @Override
    public List<Car> findAll() {
        return carCRUDRepository.findAll()
                .stream()
                .map(carMapper::mapToModel)
                .collect(Collectors.toList());
    }

}
