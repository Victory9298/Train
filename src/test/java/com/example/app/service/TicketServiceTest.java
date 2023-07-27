package com.example.app.service;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.Passenger;
import com.example.app.entity.Schedule;
import com.example.app.entity.Station;
import com.example.app.entity.Train;
import com.example.app.error.exception.BusinessException;
import com.example.app.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    TrainRepository trainRepository;
    @InjectMocks
    TrainService trainService;
    @Mock
    TicketRepository ticketRepository;
    @InjectMocks
    TicketService ticketService;
    @Mock
    ScheduleRepository scheduleRepository;
    @InjectMocks
    ScheduleService scheduleService;
    @Mock
    PassengerRepository passengerRepository;
    @InjectMocks
    PassengerService passengerService;

    @Test
    void buyTicket() {

        Integer id = 1, stationId = 1, trainId= 1;
        LocalDateTime arrivalTime = LocalDateTime.of(2023, 06,
                10,01,00, 00);
        LocalDateTime departureTime = LocalDateTime.of(2023, 06,
                10, 10, 00, 00);
        Integer placesLeft = 64;
        Station station = new Station(1, "Station 1");
        Train train = new Train(1, "12e32", 56);

        Schedule scheduleItem = new Schedule(id, station, train, arrivalTime, departureTime, 56);

        Integer passengerId = 1;
        String name = "Ivan", surname = "Ivanov";
        LocalDateTime birthDate = LocalDateTime.of(2005, 05, 12, 00, 00, 00);
        PassengerDto passengerDto = new PassengerDto(name, surname, birthDate);

        Passenger passenger = new Passenger(passengerId, name, surname, birthDate);
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(passenger);

        lenient().when(scheduleRepository.findById(1)).thenReturn(Optional.of(scheduleItem));

        lenient().when(scheduleRepository.findScheduleByTrainAndStation(Optional.of(train),
                Optional.of(station))).thenReturn(List.of(scheduleItem));

        lenient().when(passengerRepository.findPassengerByNameAndSurnameAndBirthDate("Ivan", "Ivanov", birthDate))
                .thenReturn(List.of(passenger));

        lenient().when(trainService.findTrainById(1)).thenReturn(Optional.of(train));

        lenient().when(scheduleService.checkMinutesLeftBeforeTrainTime(10, 1, 2))
                .thenReturn(true);

       String ticketResult = ticketService.buyTicket(passengerDto, 1, 2, "2023-06-10@00:00:00");
       String expectedResult = "success";
       assertEquals(ticketResult, expectedResult);
       assertNull(ticketResult);
    }

    @Test
    void getOrCreatePassenger() {

        Integer passengerId = 1;
        String name = "Ivan", surname = "Ivanon";
        LocalDateTime birthDate = LocalDateTime.of(2005, 05, 12, 00, 00, 00);
        PassengerDto passengerDto = new PassengerDto(name, surname, birthDate);
        Passenger passenger = new Passenger(passengerId, name, surname, birthDate);

        TicketService ticketService = Mockito.mock(TicketService.class);
        lenient().when(ticketService.getOrCreatePassenger(passengerDto, passengerRepository))
                .thenReturn(passenger);
    }

    @Test
    void arePlacesAvailable() {
        Schedule scheduleItem = Mockito.mock(Schedule.class);
        lenient().when(scheduleItem.getPlacesLeft()).thenReturn(10);
        Boolean arePlaces = ticketService.arePlacesAvailable(scheduleItem);
        assertEquals(arePlaces, false);
    }

    @Test
    void enoughTimeBeforeDepart() {
        ScheduleService scheduleService = Mockito.mock(ScheduleService.class);
        Boolean enoughTime = scheduleService.checkMinutesLeftBeforeTrainTime(10, 1, 2);
        assertEquals(enoughTime, false);
    }
}