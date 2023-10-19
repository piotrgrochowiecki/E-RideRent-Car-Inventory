package com.piotrgrochowiecki.eriderentcarinventory.api;

import com.piotrgrochowiecki.eriderentcarinventory.domain.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/internal/car/")
public class CarController {

    private final CarService carService;
    private final CarApiMapper carApiMapper;

    @GetMapping("{uuid}")
    public CarDto getByUuid(@PathVariable @Nullable String uuid) {
        Car car = carService.getByUuid(uuid);
        return carApiMapper.mapToDto(car);
    }

    @PostMapping("create")
    public ResponseEntity<CarDto> register(@RequestBody CarDto carDto) {
        Car carToBeAdded = carApiMapper.mapToModel(carDto);
        Car addedCar = carService.add(carToBeAdded);
        return new ResponseEntity<>(carApiMapper.mapToDto(addedCar), HttpStatus.CREATED);
    }

}
