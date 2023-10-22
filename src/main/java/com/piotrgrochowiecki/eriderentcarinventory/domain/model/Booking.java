package com.piotrgrochowiecki.eriderentcarinventory.domain.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Booking(Long id,
                      LocalDate startDate,
                      LocalDate endDate,
                      String userUuid,
                      String carUuid) {

}
