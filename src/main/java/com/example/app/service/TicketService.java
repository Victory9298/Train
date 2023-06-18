package com.example.app.service;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.*;
import com.example.app.error.ExceptionMessage;
import com.example.app.error.exception.BusinessException;
import com.example.app.repository.PassengerRepository;
import com.example.app.repository.ScheduleDBRepository;
import com.example.app.repository.ScheduleRepository;
import com.example.app.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerService passengerService;
    Logger logger = LoggerFactory.getLogger(TicketService.class);

    public TicketService() {};

    public Iterable<Ticket> getAllRegisteredPassengers() {

        return  repository.getAllRegisteredPassengers();
    }
    public void buyTicket(PassengerDto passengerDto, Integer scheduleId,
                          Integer end_station_id, CharSequence arrivalTime) {

        Optional<Schedule> schedule = Optional.ofNullable(scheduleService.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND)));

        Schedule scheduleItem = schedule.get();

        Integer places_left = scheduleItem.getPlaces_left();

        Passenger passenger;
        Boolean buyTicket = true;

        if (places_left < 1) {
            logger.warn("No places left. Ticket can't be bought.");
            buyTicket = false;
        }
        Integer train_id = scheduleItem.getTrain().getId();
        Integer station_id = scheduleItem.getStation().getId();
        List <Passenger> passengers = passengerRepository.findPassengerByNameAndSurnameAndBirthDate(
                passengerDto.getName(),
                passengerDto.getSurname(),
                passengerDto.getBirthDate());

        if (!scheduleService.checkMinutesLeftBeforeTrainTime(10, train_id, station_id)) {
            logger.warn("checkMinutesLeftBeforeTrainTime: less than 10 minutes before train departure");
            buyTicket = false;
        }

        if (passengers.size() == 0) {
            logger.info("Passenger with name " + passengerDto.getName() + " and surname " + passengerDto.getSurname()
                    + " and birthday " + passengerDto.getBirthDate() + " isn't found. A new passenger will be added.");
            passenger = passengerService.addNewPassenger(passengerDto);
        } else {
            passenger = passengers.get(0);
        }

        if (buyTicket) {

            LocalDateTime arrivalTimeDB = LocalDateTime.parse(arrivalTime, DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss"));

            Ticket ticket = Ticket.builder()
                    .trainId(scheduleItem.getTrain().getId())
                    .passengerId(passenger.getId())
                    .startStationId(scheduleItem.getStation().getId())
                    .endStationId(end_station_id)
                    .departureTime(scheduleItem.getDepartureTime())
                    .arrivalTime(arrivalTimeDB)
                    .build();

            repository.save(ticket);

            scheduleItem.setPlaces_left(places_left - 1);
        }
    }

    public Optional<Ticket> findTicketById(Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND)));
    }

    public void deletTicketById(Integer id) {
        findTicketById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        repository.deleteById(id);
    }
}
