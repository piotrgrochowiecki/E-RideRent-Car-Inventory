package com.piotrgrochowiecki.eriderentcarinventory.remote.dto;

import lombok.Builder;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
public record CarCreateRequestDto(String uuid,
                                  String brand, //TODO -> dodać validatory z javax.validation. Walidacja powinna być robiona możliwie wysoko
                                  String model,
                                  String planeNumber) {

}
