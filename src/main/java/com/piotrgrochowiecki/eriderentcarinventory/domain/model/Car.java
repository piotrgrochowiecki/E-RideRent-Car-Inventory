package com.piotrgrochowiecki.eriderentcarinventory.domain.model;

import lombok.Builder;

@Builder
public record Car(Long id,
                  String uuid,
                  String brand,
                  String model,
                  String plateNumber) {

}
