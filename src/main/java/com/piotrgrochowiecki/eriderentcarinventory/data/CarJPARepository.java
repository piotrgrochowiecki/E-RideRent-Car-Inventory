package com.piotrgrochowiecki.eriderentcarinventory.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarJPARepository extends JpaRepository<CarEntity, Long> {

    Optional<CarEntity> findByPlateNumber(String plateNumber);

    Optional<CarEntity> findByUuid(String uuid);

    Page<CarEntity> findAll(Pageable pageable);

    boolean existsByPlateNumber(String plateNumber);

}
