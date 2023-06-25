package com.example.app.service;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.Passenger;
import com.example.app.entity.Schedule;
import com.example.app.error.ExceptionMessage;
import com.example.app.error.exception.BusinessException;
import com.example.app.repository.PassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class PassengerService {

    @Autowired
    private PassengerRepository repository;
    Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    public PassengerService() {}
    public PassengerService(PassengerRepository repository) {this.repository = repository;}
    public List<Passenger> findAllPassengers() { return repository.findAll(); }
    public Passenger findPassengerById(int id) {
        return repository.findPassengerById(id);
    }
    public Passenger addNewPassenger(PassengerDto passengerDto) {

        String name = passengerDto.getName();
        String surname = passengerDto.getSurname();
        LocalDateTime birthDate = passengerDto.getBirthDate();

        List<Passenger> passengersDB = repository.findPassengerByNameAndSurnameAndBirthDate(name, surname, birthDate);

        if (passengersDB.size() == 0) {

            Passenger passenger = Passenger.builder()
                    .name(name)
                    .surname(surname)
                    .birthDate(birthDate)
                    .build();
            return repository.save(passenger);
        } else {
            logger.warn("Passenger with name " + name + " and surname " + surname + " and birthday " + birthDate + " already exists in database");
            throw new BusinessException(ExceptionMessage.OBJECT_ALREADY_EXISTS);
        }
    }
    public List<Passenger> findPassengerByNameAndSurnameAndBirthDate(String name, String surname, CharSequence birthDate) {
        LocalDate parsedBD = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDateTime birthDateTime = parsedBD.atStartOfDay();
        return repository.findPassengerByNameAndSurnameAndBirthDate(name, surname, birthDateTime);
    }

    public void deletePassenger(int id) {
        repository.findById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        repository.deleteById(id);
    }

}
