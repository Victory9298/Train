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

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {

    @Autowired
    public TicketRepository repository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    public ScheduleRepository scheduleRepository;
    @Autowired
    public PassengerRepository passengerRepository;
    @Autowired
    public PassengerService passengerService;
    Logger logger = LoggerFactory.getLogger(TicketService.class);

    private static final int MIN_PERIOD_BEFORE_DEPART = 10;

    public TicketService() {};

    public TicketService(TicketRepository repository) {this.repository = repository;};

    public Iterable<Ticket> getAllRegisteredPassengers() {

        return  repository.getAllRegisteredPassengers();
    }

    public String buyTicket(PassengerDto passengerDto, Integer scheduleId,
                            Integer end_station_id, CharSequence arrivalTime) {

        Schedule scheduleItem = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));

        Integer train_id = scheduleItem.getTrain().getId();
        Integer station_id = scheduleItem.getStation().getId();

        Passenger passenger = getOrCreatePassenger(passengerDto, passengerRepository);
        Boolean arePlacesAvailable = arePlacesAvailable(scheduleItem);
        Boolean enoughTimeBeforeDepart = enoughTimeBeforeDepart(train_id, station_id);

        if (arePlacesAvailable && enoughTimeBeforeDepart) {

            LocalDateTime arrivalTimeDB = LocalDateTime.parse(arrivalTime, DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss"));
            Ticket ticket = Ticket.builder()
                    .trainId(train_id)
                    .passengerId(passenger.getId())
                    .startStationId(station_id)
                    .endStationId(end_station_id)
                    .departureTime(scheduleItem.getDepartureTime())
                    .arrivalTime(arrivalTimeDB)
                    .build();

            repository.save(ticket);

            scheduleItem.setPlacesLeft(scheduleItem.getTrain().getPlacesNumber() - 1);
            return "success";
        } else {

            String message = "";

            if (!arePlacesAvailable) {
                message = "Not enough places";
            } else {
                message = "It is too late";
            }
//            throw new BusinessException(message);
            return message;
        }
    }

    public Boolean arePlacesAvailable(Schedule scheduleItem) {

        Integer placesLeft = scheduleItem.getPlacesLeft();
        if (placesLeft < 1) {
            String errorMessage = "No places left. Ticket can't be bought.";
            logger.error(errorMessage);
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean enoughTimeBeforeDepart(Integer train_id, Integer station_id) {

        if (!scheduleService.checkMinutesLeftBeforeTrainTime(MIN_PERIOD_BEFORE_DEPART, train_id, station_id)) {
            String errorMessage = "Less than 10 minutes before train departure. Ticket can't be bought.";
            logger.error(errorMessage);
            return false;
        } else {
            return true;
        }
    }

    public Passenger getOrCreatePassenger(PassengerDto passengerDto, PassengerRepository passengerRepository) {

        Passenger passenger;

        List <Passenger> passengers = passengerRepository.findPassengerByNameAndSurnameAndBirthDate(
                passengerDto.getName(),
                passengerDto.getSurname(),
                passengerDto.getBirthDate());

        if (passengers.size() == 0) {
            logger.info("Passenger with name " + passengerDto.getName() + " and surname " + passengerDto.getSurname()
                    + " and birthday " + passengerDto.getBirthDate() + " isn't found. A new passenger will be added.");
            passenger = passengerService.addNewPassenger(passengerDto);
        } else {
            passenger = passengers.get(0);
        }

        return passenger;
    }

    public Optional<Ticket> findTicketById(Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND)));
    }

    public void deletTicketById(Integer id) {
        findTicketById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        repository.deleteById(id);
    }
}
