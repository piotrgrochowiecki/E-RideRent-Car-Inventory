package com.piotrgrochowiecki.eriderentcarinventory.remote.dto;

import lombok.Builder;

@Builder
public record CarResponseDto(Long id,
                             String uuid,
                             String brand,
                             String model,
                             String plateNumber) {
}
