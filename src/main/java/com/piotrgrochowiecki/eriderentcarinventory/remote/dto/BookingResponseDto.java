package com.piotrgrochowiecki.eriderentcarinventory.remote.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookingResponseDto(Long id,
                                 LocalDate startDate,
                                 LocalDate endDate,
                                 String userUuid,
                                 String carUuid) {

}
