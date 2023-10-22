package com.piotrgrochowiecki.eriderentcarinventory.api.dto;

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
