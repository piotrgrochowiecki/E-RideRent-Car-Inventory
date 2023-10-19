package com.piotrgrochowiecki.eriderentcarinventory.api;

import com.piotrgrochowiecki.eriderentcarinventory.domain.CarAlreadyExistsRuntimeException;
import com.piotrgrochowiecki.eriderentcarinventory.domain.NotFoundRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundRuntimeException.class)
    public RuntimeExceptionDto handleNotFoundRuntimeException(NotFoundRuntimeException exception) {
        return RuntimeExceptionDto.builder()
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CarAlreadyExistsRuntimeException.class)
    public RuntimeExceptionDto handleCarAlreadyExistsRuntimeException(CarAlreadyExistsRuntimeException exception) {
        return RuntimeExceptionDto.builder()
                .message(exception.getMessage())
                .timeStamp(LocalDateTime.now())
                .build();
    }
}
