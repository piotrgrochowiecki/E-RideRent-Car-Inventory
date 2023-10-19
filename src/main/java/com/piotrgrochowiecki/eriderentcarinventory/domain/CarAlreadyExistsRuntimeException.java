package com.piotrgrochowiecki.eriderentcarinventory.domain;

public class CarAlreadyExistsRuntimeException extends RuntimeException {

    public CarAlreadyExistsRuntimeException(String plateNumber) {
        super("Car with plate number " + plateNumber + " already exists");
    }

}
