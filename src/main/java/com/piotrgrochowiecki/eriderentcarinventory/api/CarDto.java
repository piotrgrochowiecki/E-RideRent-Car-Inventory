package com.piotrgrochowiecki.eriderentcarinventory.api;

import lombok.Builder;

@Builder
public record CarDto(String uuid,
                     String brand,
                     String model,
                     String planeNumber) {

}
