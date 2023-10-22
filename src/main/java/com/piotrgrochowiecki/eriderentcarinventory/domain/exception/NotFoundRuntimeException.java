package com.piotrgrochowiecki.eriderentcarinventory.domain.exception;

public class NotFoundRuntimeException extends RuntimeException {

    public NotFoundRuntimeException(String uuid) {
        super("Car with uuid " + uuid + " has not been found");
    }

}
