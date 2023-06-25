package com.example.app.service;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.Passenger;
import com.example.app.error.ExceptionMessage;
import com.example.app.error.exception.BusinessException;
import com.example.app.repository.PassengerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.jdbc.Sql;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {
    private static final int id = 1;
    private static final LocalDateTime birthDate = LocalDateTime.of(2005, 05, 12, 00, 00,00);;
    private static final String name = "Ivan", surname = "Ivanov";
    @Mock PassengerRepository repo;
    @InjectMocks  PassengerService passengerService;
    Passenger passenger;
    @BeforeEach
    void setUp() {
        passenger = new Passenger(1, name, surname, birthDate);
    }
    @Test
    void findPassengerByIdShouldFindPassenger() {

        when(repo.findPassengerById(id)).thenReturn(passenger);
        Passenger result1 = passengerService.findPassengerById(1);
        Passenger result2 = passengerService.findPassengerById(2);

        assertEquals(id, result1.getId());
        assertNull(result2);
    }

    @Test
    void findPassengerByIdShouldCallRepository() {

        Passenger passenger = Mockito.mock(Passenger.class);
        when(repo.findPassengerById(id)).thenReturn(passenger);

        final Passenger actual = passengerService.findPassengerById(id);

        assertNotNull(actual);
        assertEquals(passenger, actual);
        verify(repo).findPassengerById(id);
    }

    @Test
    void findPassengerByNameAndSurnameAndBirthDateAsDate() {

        List<Passenger> passenger = Collections.singletonList(mock(Passenger.class));
        when(repo.findPassengerByNameAndSurnameAndBirthDate(name, surname, birthDate)).thenReturn(passenger);

        final List<Passenger> actual = passengerService.findPassengerByNameAndSurnameAndBirthDate(name, surname, "20050512");

        assertNotNull(actual);
        assertEquals(passenger, actual);
        verify(repo).findPassengerByNameAndSurnameAndBirthDate(name, surname, birthDate);
    }

}