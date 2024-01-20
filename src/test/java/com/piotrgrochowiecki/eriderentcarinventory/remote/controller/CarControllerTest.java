package com.piotrgrochowiecki.eriderentcarinventory.remote.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.piotrgrochowiecki.eriderentcarinventory.domain.exception.NotFoundRuntimeException;
import com.piotrgrochowiecki.eriderentcarinventory.domain.model.Car;
import com.piotrgrochowiecki.eriderentcarinventory.domain.service.CarService;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreateRequestDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarCreatedResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.CarResponseDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.dto.RuntimeExceptionDto;
import com.piotrgrochowiecki.eriderentcarinventory.remote.mapper.CarApiMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private CarService carService;
    @MockBean
    private CarApiMapper carApiMapper;
    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;

    private static String uuid1;
    private static String uuid2;
    private static String uuid3;
    private static ObjectMapper mapper;
    private static Instant fixedTimestamp;

    @Autowired
    CarControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @BeforeAll
    static void setUp() {
        uuid1 = UUID.randomUUID().toString();
        uuid2 = UUID.randomUUID().toString();
        uuid3 = UUID.randomUUID().toString();
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        Clock fixedClock = Clock.fixed(Instant.parse("2018-08-22T10:00:00Z"),
                ZoneOffset.UTC);
        fixedTimestamp = Instant.now(fixedClock);
    }

    @Test
    void shouldReturnOkStatusAndCarResponseDto() throws Exception {
        //given
        Car car = Car.builder()
                .id(1L)
                .uuid(uuid1)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WCI 53326")
                .build();

        CarResponseDto carResponseDto = CarResponseDto.builder()
                .id(1L)
                .uuid(uuid1)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WCI 53326")
                .build();

        when(carService.getByUuid(uuid1))
                .thenReturn(car);

        when(carApiMapper.mapToCarResponseDto(car))
                .thenReturn(carResponseDto);

        String carResponseDtoAsJson = mapper.writeValueAsString(carResponseDto);

        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/car/" + uuid1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(carResponseDtoAsJson));
    }

    @Test
    void shouldReturnNotFoundStatus() throws Exception {
        //given
        NotFoundRuntimeException notFoundRuntimeException = new NotFoundRuntimeException(uuid1);

        RuntimeExceptionDto runtimeExceptionDto = RuntimeExceptionDto.builder()
                .message(notFoundRuntimeException.getMessage())
                .timeStamp(LocalDateTime.ofInstant(fixedTimestamp, ZoneOffset.UTC))
                .build();

        when(carService.getByUuid(uuid1))
                .thenThrow(notFoundRuntimeException);
        when(globalExceptionHandler.handleNotFoundRuntimeException(notFoundRuntimeException))
                .thenReturn(runtimeExceptionDto);

        String runtimeExceptionDtoAsJson = mapper.writeValueAsString(runtimeExceptionDto);

        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/car/" + uuid1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status()
                        .isNotFound())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(runtimeExceptionDtoAsJson));
    }

    @Test
    void shouldReturnOkStatusAndPageOfTreeCarResponseDtos() throws Exception {
        //given
        Car car1 = Car.builder()
                .id(1L)
                .uuid(uuid1)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WCI 53326")
                .build();

        Car car2 = Car.builder()
                .id(2L)
                .uuid(uuid2)
                .brand("VW")
                .model("ID.4")
                .plateNumber("WP 4533")
                .build();

        Car car3 = Car.builder()
                .id(3L)
                .uuid(uuid3)
                .brand("Renault")
                .model("Zoe")
                .plateNumber("PO 64542")
                .build();

        List<Car> carList = List.of(car1, car2, car3);
        Page<Car> carPage = new PageImpl<>(carList);
        String carPageAsJson = mapper.writeValueAsString(carPage);

        CarResponseDto carResponseDto1 = CarResponseDto.builder()
                .id(1L)
                .uuid(uuid1)
                .brand("Tesla")
                .model("Model X")
                .plateNumber("WCI 53326")
                .build();

        CarResponseDto carResponseDto2 = CarResponseDto.builder()
                .id(2L)
                .uuid(uuid2)
                .brand("VW")
                .model("ID.4")
                .plateNumber("WP 4533")
                .build();

        CarResponseDto carResponseDto3 = CarResponseDto.builder()
                .id(3L)
                .uuid(uuid3)
                .brand("Renault")
                .model("Zoe")
                .plateNumber("PO 64542")
                .build();

        when(carApiMapper.mapToCarResponseDto(car1))
                .thenReturn(carResponseDto1);
        when(carApiMapper.mapToCarResponseDto(car2))
                .thenReturn(carResponseDto2);
        when(carApiMapper.mapToCarResponseDto(car3))
                .thenReturn(carResponseDto3);

        when(carService.getAll(1, 3, "id"))
                .thenReturn(carPage);

        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/car/all")
                        .param("pageNumber", "1")
                        .param("pageSize", "3")
                        .param("propertyToSortBy", "id"))
                .andDo(print())
                .andExpect(status()
                        .isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(carPageAsJson));
    }

    @Test
    void shouldReturnCreatedStatusAndCarCreatedResponseDto() throws Exception {
        //given
        CarCreateRequestDto carCreateRequestDto = CarCreateRequestDto.builder()
                .brand("Tesla")
                .model("Model S")
                .plateNumber("WY 42332")
                .build();

        String carCreateRequestDtoAsJson = mapper.writeValueAsString(carCreateRequestDto);

        Car carToBeAdded = Car.builder()
                .brand("Tesla")
                .model("Model S")
                .plateNumber("WY 42332")
                .build();

        Car addedCar = Car.builder()
                .id(3L)
                .uuid(uuid3)
                .brand("Tesla")
                .model("Model S")
                .plateNumber("WY 42332")
                .build();

        CarCreatedResponseDto carCreatedResponseDto = CarCreatedResponseDto.builder()
                .brand("Tesla")
                .model("Model S")
                .plateNumber("WY 42332")
                .build();

        String carCreatedResponseDtoAsJson = mapper.writeValueAsString(carCreatedResponseDto);

        when(carApiMapper.mapToModel(carCreateRequestDto))
                .thenReturn(carToBeAdded);
        when(carService.add(carToBeAdded))
                .thenReturn(addedCar);
        when(carApiMapper.mapToCarCreatedResponseDto(addedCar))
                .thenReturn(carCreatedResponseDto);

        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carCreateRequestDtoAsJson))
                .andDo(print())
                .andExpect(status()
                        .isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json(carCreatedResponseDtoAsJson));
    }

    @Test
    void shouldReturnBarRequestStatus() throws Exception {
        //given
        CarCreateRequestDto carCreateRequestDto = CarCreateRequestDto.builder()
                .brand(" ")
                .model(" ")
                .plateNumber(" ")
                .build();

        String carCreateRequestDtoAsJson = mapper.writeValueAsString(carCreateRequestDto);

        //when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/car/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carCreateRequestDtoAsJson))
                .andDo(print())
                .andExpect(status()
                        .isBadRequest());
    }
}