package com.piotrgrochowiecki.eriderentcarinventory.api.dto;

import lombok.Builder;

@Builder
public record CarDto(String uuid,
                     String brand,
                     String model,
                     String planeNumber) {

}
