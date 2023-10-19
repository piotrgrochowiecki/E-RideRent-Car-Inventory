package com.piotrgrochowiecki.eriderentcarinventory.domain;

public class NotFoundRuntimeException extends RuntimeException {

    public NotFoundRuntimeException(String uuid) {
        super("Car with uuid " + uuid + " has not been found");
    }

}
