package com.example.app.repository;

import com.example.app.entity.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;
    String name = "Ivan", surname = "Ivanov";
    LocalDateTime birthDate = LocalDateTime.of(2005, 05, 12, 00, 00, 00);
    Passenger passenger;

    @BeforeEach
    void setUp() {
         passenger = new Passenger(1, name, surname, birthDate);
    }

    @Test
    void findPassengerById() {

        passengerRepository.save(passenger);

        assertEquals(passengerRepository.findPassengerById(1), passenger);
        assertNotEquals(passengerRepository.findPassengerById(2), passenger);
    }

    @Test
    void findPassengerByNameAndSurnameAndBirthDate() {

        passengerRepository.save(passenger);
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        assertEquals(passengerRepository.findPassengerByNameAndSurnameAndBirthDate(name, surname, birthDate), passengers);
    }

    @Test
    void deletePassengerById() {

        passengerRepository.save(passenger);
        passengerRepository.deleteById(passenger.getId());
        Optional<Passenger> passengerOptional = passengerRepository.findById(passenger.getId());
        assertThat(passengerOptional).isEmpty();
    }
}