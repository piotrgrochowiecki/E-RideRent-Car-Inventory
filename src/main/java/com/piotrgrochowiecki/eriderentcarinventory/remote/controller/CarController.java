package com.piotrgrochowiecki.eriderentcarinventory.remote.controller;

import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreateRequestDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreatedResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.mapper.CarApiMapper;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.service.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/car/")
@Validated
public class CarController {

    private final CarService carService;
    private final CarApiMapper carApiMapper;

    @GetMapping("{uuid}")
    public CarResponseDto getByUuid(@PathVariable("uuid") @NotBlank String uuid) {
        Car car = carService.getByUuid(uuid);
        return carApiMapper.mapToCarResponseDto(car);
    }

    @GetMapping("all")
    public Page<CarResponseDto> getAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(defaultValue = "id") String propertyToSortBy) {
        return carService.getAll(pageNumber, pageSize, propertyToSortBy)
                .map(carApiMapper::mapToCarResponseDto);
    }

    @GetMapping("available/{startDate}/{endDate}")
    public List<CarResponseDto> getAvailableCars(@PathVariable("startDate") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                 @PathVariable("endDate") @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        return carService.getAvailableCars(startDate, endDate).stream()
                .map(carApiMapper::mapToCarResponseDto)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<CarCreatedResponseDto> register(@Valid @RequestBody CarCreateRequestDto carCreateRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors()
                    .forEach(fieldError -> System.out.println(fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n"));
        }
        Car carToBeAdded = carApiMapper.mapToModel(carCreateRequestDto);
        Car addedCar = carService.add(carToBeAdded);
        return new ResponseEntity<>(carApiMapper.mapToCarCreatedResponseDto(addedCar), HttpStatus.CREATED);
    }

}
