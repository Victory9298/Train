package com.example.app.service;

import com.example.app.entity.Train;
import com.example.app.repository.PassengerRepository;
import com.example.app.repository.ScheduleDBRepository;
import com.example.app.repository.ScheduleRepository;
import com.example.app.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    ScheduleRepository repo;
    @InjectMocks
    ScheduleService scheduleService;
    @Mock
    ScheduleDBRepository scheduleDBRepository;
    @InjectMocks
    ScheduleDBService scheduleDBService;
    @InjectMocks
    StationService stationService;
    @Mock
    ScheduleRepository scheduleRepository;
    @InjectMocks
    TrainService trainService;
    @Mock TrainRepository trainRepository;


    @BeforeEach
    void setUp() {
        this.trainService = new TrainService(this.trainRepository);
        this.scheduleService = new ScheduleService(this.repo, this.scheduleDBService, this.stationService, this.trainService);
    }

    @Test
    void getTrainFromToStation() {

        LocalDateTime fromTime = LocalDateTime.of(2023, 05,
                12, 10, 00, 00);
        LocalDateTime toTime = LocalDateTime.of(2023, 05,
                12, 20, 00, 00);

        Train train = new Train(1, "123", 37);

        List<Train> trains = new ArrayList<>();
        trains.add(train);

        lenient().when(scheduleDBService.getTrainFromStation(1, fromTime, toTime))
                .thenReturn(trains);
        lenient().when(scheduleDBService.getTrainToStation(2, fromTime))
                .thenReturn(trains);

        List<Train> resultTrains = scheduleService.getTrainFromToStation(
                        fromTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss")),
                        toTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss")), 1, 2);

        assertNotNull(trains);
        assertEquals(resultTrains, trains);
    }


}