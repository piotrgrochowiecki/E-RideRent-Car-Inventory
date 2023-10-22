package com.piotrgrochowiecki.eriderentcarinventory.api.controller;

import com.piotrgrochowiecki.eriderentcarinventory.api.dto.CarDto;
import com.piotrgrochowiecki.eriderentcarinventory.api.mapper.ApiMapper;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/internal/car/")
public class CarController {

    private final CarService carService;
    private final ApiMapper apiMapper;

    @GetMapping("{uuid}")
    public CarDto getByUuid(@PathVariable @Nullable String uuid) {
        Car car = carService.getByUuid(uuid);
        return apiMapper.mapToDto(car);
    }

    @PostMapping("create")
    public ResponseEntity<CarDto> register(@RequestBody CarDto carDto) {
        Car carToBeAdded = apiMapper.mapToModel(carDto);
        Car addedCar = carService.add(carToBeAdded);
        return new ResponseEntity<>(apiMapper.mapToDto(addedCar), HttpStatus.CREATED);
    }

    @GetMapping("available/{startDate}/{endDate}")
    public List<CarDto> getAvailableCars(@PathVariable @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                      @PathVariable @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return carService.getAvailableCars(startDate, endDate).stream()
                .map(apiMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
