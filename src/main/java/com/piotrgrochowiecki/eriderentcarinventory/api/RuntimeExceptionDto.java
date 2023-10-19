package com.piotrgrochowiecki.eriderentcarinventory.api;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RuntimeExceptionDto(String message, LocalDateTime timeStamp) {

}
