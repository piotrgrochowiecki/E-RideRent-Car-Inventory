package com.piotrgrochowiecki.eriderentcarinventory.remote.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CarCreateRequestDto(@NotBlank String brand,
                                  @NotBlank String model,
                                  @NotBlank String plateNumber) {

}
