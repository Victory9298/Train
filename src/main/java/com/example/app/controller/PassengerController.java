package com.example.app.controller;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.Passenger;
import com.example.app.entity.Train;
import com.example.app.repository.PassengerRepository;
import com.example.app.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Tag(description = "Api to manage passengers",
        name = "Passenger Resource")
@SecurityRequirement(name = "Authorization")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @Operation(summary = "Get all passengers",
            description = "Provides a list of all passengers")
    @GetMapping("/passenger/all")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public List<Passenger> all() {
        return passengerService.findAllPassengers();
    }

    @Operation(summary = "Get passenger by id",
            description = "Provides passenger entity by id")
    @GetMapping("/passenger/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public Passenger findPassengerById(@PathVariable int id) {
        return passengerService.findPassengerById(id);
    }

    @Operation(summary = "Find passenger by name, surname and birth date",
            description = "Provides a list of passengers, found by name, surname and birth date")
    @GetMapping("/passenger/find")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public List<Passenger> findPassengerByNameAndSurnameAndBirthDate(@RequestParam String name,
                                                                     @RequestParam String surname,
                                                                     @RequestParam CharSequence birthDate) {
        return passengerService.findPassengerByNameAndSurnameAndBirthDate(name, surname, birthDate);
    }

    @Operation(summary = "Add passenger",
            description = "Add passenger")
    @PostMapping(path="/passenger")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public @ResponseBody Passenger addNewPassenger(@RequestBody PassengerDto passengerDto) {
        return passengerService.addNewPassenger(passengerDto);
    }

    @Operation(summary = "Delete passenger by id",
            description = "Delete passenger by id or throw exception")
    @DeleteMapping("/passenger/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    void deletePassenger(@PathVariable int id) {
        passengerService.deletePassenger(id);
    }

}
