package com.piotrgrochowiecki.eriderentcarinventory.remote.dto;

import lombok.Builder;

@Builder
public record CarCreatedResponseDto(String brand,
                                    String model,
                                    String plateNumber) {
}
