package com.piotrgrochowiecki.eriderentcarinventory.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarCRUDRepository extends CrudRepository<CarEntity, Long> {

    Optional<CarEntity> findByPlateNumber(String plateNumber);

    Optional<CarEntity> findByUuid(String uuid);

    List<CarEntity> findAll();

}
