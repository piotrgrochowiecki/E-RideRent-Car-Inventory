package com.piotrgrochowiecki.eriderentcarinventory.domain.service;

import com.piotrgrochowiecki.eriderentcarinventory.domain.exception.NotFoundRuntimeException;
import com.piotrgrochowiecki.eriderentcarinventory.domain.client.BookingManagementApiClientService;
import com.piotrgrochowiecki.eriderentcarinventory.domain.exception.CarAlreadyExistsRuntimeException;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Booking;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final BookingManagementApiClientService bookingManagementClient;

    public Car add(@Nullable Car car) {
        assert car != null; //TODO assert do wyrzucenia, @Nullable też jeśli sprawdzanie wykonywane jest wyżej
        if (doesCarAlreadyExists(car.plateNumber())) {
            throw new CarAlreadyExistsRuntimeException(car.plateNumber());
        }
        return carRepository.save(car);
    }

    public Car getById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new NotFoundRuntimeException("id"));
    }

    public Car getByUuid(@Nullable String uuid) {
        assert uuid != null;
        if(carRepository.findByUuid(uuid)
                .isEmpty()) {
            throw new NotFoundRuntimeException(uuid);
        }
        return carRepository.findByUuid(uuid).get();
    }
    //TODO zmniejszyć tabulację na np. 2 lub 3 znaki (obecnie prawdopodobnie 4)

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public List<Car> getAvailableCars(LocalDate newBookingStartDate, LocalDate newBookingEndDate) {
        List<Booking> existingBookingsInRequestedPeriod = bookingManagementClient.getAllBookingsOverlappingWithDates(newBookingStartDate, newBookingEndDate);
        List<Car> carsNotAvailable = new ArrayList<>();

        for(Booking booking : existingBookingsInRequestedPeriod) {
            Optional<Car> carOptional = carRepository.findByUuid(booking.carUuid());
            carsNotAvailable.add(carOptional.get());
        }
        //TODO spróbować wszystko przerobić na jeden stream
        //TODO różnice między streamem, a pętlą for: stream jest mniej wydajny przy dużej ilości danych -> poczytać

        List<Car> availableCars = getAll();
        availableCars.removeAll(carsNotAvailable);
        return availableCars;

    }

    private boolean doesCarAlreadyExists(String plateNumber) {
        return carRepository.findByPlateNumber(plateNumber)
                .isPresent(); //TODO przerobić w repo na existBy, bo wtedy nie pobieramy całego obiektu skoro nie potrzebujemy
    }

}