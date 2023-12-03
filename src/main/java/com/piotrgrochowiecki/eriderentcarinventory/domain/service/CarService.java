package com.piotrgrochowiecki.eriderentcarinventory.domain.service;

import com.piotrgrochowiecki.eriderentcarinventory.domain.exception.NotFoundRuntimeException;
import com.piotrgrochowiecki.eriderentcarinventory.domain.client.BookingManagementApiClientService;
import com.piotrgrochowiecki.eriderentcarinventory.domain.exception.CarAlreadyExistsRuntimeException;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final BookingManagementApiClientService bookingManagementClient;

    public Car add(Car car) {
        if (carRepository.existsByPlateNumber(car.plateNumber())) {
            throw new CarAlreadyExistsRuntimeException(car.plateNumber());
        }
        return carRepository.save(car);
    }

    public Car getById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundRuntimeException(id.toString()));
    }

    public Car getByUuid(String uuid) {
        return carRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundRuntimeException(uuid));
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public List<Car> getAvailableCars(LocalDate newBookingStartDate, LocalDate newBookingEndDate) {
        List<Car> carsNotAvailable = getCarsNotAvailable(newBookingStartDate, newBookingEndDate);
        return carRepository.findAll()
                .stream()
                .filter(car -> !carsNotAvailable.contains(car))
                .collect(Collectors.toList());
    }

    private List<Car> getCarsNotAvailable(LocalDate newBookingStartDate, LocalDate newBookingEndDate) {
        List<Booking> existingBookingsInRequestedPeriod = bookingManagementClient.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);
        return existingBookingsInRequestedPeriod.stream()
                .map(booking -> carRepository.findByUuid(booking.carUuid()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

}
