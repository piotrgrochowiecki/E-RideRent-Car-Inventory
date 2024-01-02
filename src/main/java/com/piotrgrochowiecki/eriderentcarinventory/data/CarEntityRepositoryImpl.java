package com.piotrgrochowiecki.eriderentcarinventory.data;

import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CarEntityRepositoryImpl implements CarRepository {

    private final CarJPARepository carJPARepository;
    private final CarMapper carMapper;

    @Override
    public Car save(@Nullable Car car) {
        assert car != null;
        CarEntity carEntity = carMapper.mapToEntity(car);
        UUID uuid = UUID.randomUUID();
        carEntity.setUuid(uuid.toString());
        CarEntity savedCarEntity = carJPARepository.save(carEntity);
        return carMapper.mapToModel(savedCarEntity);
    }

    @Override
    public Optional<Car> findById(Long id) {
        assert id != null;
        return carJPARepository.findById(id)
                .map(carMapper::mapToModel);
    }

    @Override
    public Optional<Car> findByUuid(String uuid) {
        assert uuid != null;
        return carJPARepository.findByUuid(uuid)
                .map(carMapper::mapToModel);
    }

    @Override
    public Optional<Car> findByPlateNumber(String plateNumber) {
        assert plateNumber != null;
        return carJPARepository.findByPlateNumber(plateNumber)
                .map(carMapper::mapToModel);
    }

    @Override
    public List<Car> findAll() {
        return carJPARepository.findAll()
                .stream()
                .map(carMapper::mapToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Car> findAll(Pageable paging) {
        return carJPARepository.findAll(paging)
                .map(carMapper::mapToModel);
    }

    @Override
    public boolean existsByPlateNumber(String plateNumber) {
        return carJPARepository.existsByPlateNumber(plateNumber);
    }

}
