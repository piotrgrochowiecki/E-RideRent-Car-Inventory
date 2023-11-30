package com.piotrgrochowiecki.eriderentcarinventory.remote.controller;

import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreateRequestDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.mapper.ApiMapper;
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
@RequestMapping("api/v1/car/")
public class CarController {

    private final CarService carService;
    private final ApiMapper apiMapper;

    @GetMapping("{uuid}")
    public CarCreateRequestDto getByUuid(@PathVariable @Nullable String uuid) {
        Car car = carService.getByUuid(uuid);
        return apiMapper.mapToDto(car);
    }

    @PostMapping()
    public ResponseEntity<CarCreateRequestDto> register(@RequestBody CarCreateRequestDto carCreateRequestDto) {
        Car carToBeAdded = apiMapper.mapToModel(carCreateRequestDto);
        Car addedCar = carService.add(carToBeAdded);
        return new ResponseEntity<>(apiMapper.mapToDto(addedCar), HttpStatus.CREATED);
    }

    @GetMapping("available/{startDate}/{endDate}") //TODO przenieść te daty do query param
    public List<CarCreateRequestDto> getAvailableCars(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                      @PathVariable @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return carService.getAvailableCars(startDate, endDate).stream()
                .map(apiMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
