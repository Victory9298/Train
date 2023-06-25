package com.example.app.controller;

import com.example.app.entity.Passenger;
import com.example.app.repository.PassengerRepository;
import com.example.app.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest
class PassengerControllerTest {

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private PassengerService passengerService;

    String name = "Ivan", surname = "Ivanov";
    LocalDateTime birthDate = LocalDateTime.of(2005, 05, 12, 00, 00, 00);
    Passenger passenger;

    @BeforeEach
    void setUp() {
        passenger = new Passenger(1, name, surname, birthDate);
    }

    @Test
    void findPassengerById() throws Exception {

       passengerRepository.save(passenger);
       Integer passengerId = passenger.getId();
       given(passengerService.findPassengerById(passengerId)).willReturn(passenger);

       ResultActions response = mockMvc.perform(get("/passenger/{id}", passengerId));

       response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(passenger.getId())))
                .andExpect(jsonPath("$.name", is(passenger.getName())))
                .andExpect(jsonPath("$.surname", is(passenger.getSurname())));

    }
}