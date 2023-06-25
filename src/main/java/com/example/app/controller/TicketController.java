package com.example.app.controller;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.Ticket;
import com.example.app.repository.TicketRepository;
import com.example.app.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@RestController
@Tag(description = "Api to manage tickets",
        name = "Ticket Resource")
@SecurityRequirement(name = "Authorization")
public class TicketController {

    private final TicketRepository repository;
    private TicketService ticketService;

    public TicketController(TicketRepository repository, TicketService ticketService) {
        this.repository = repository;
        this.ticketService = ticketService;
    }
    @Operation(summary = "Get all passengers",
            description = "Provides a list of all passengers that bought tickets")
    @GetMapping("/ticket/all")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    Iterable<Ticket> getAllRegisteredPassengers() {
       return ticketService.getAllRegisteredPassengers();
    }

    @Operation(summary = "Buy ticket",
            description = "Check if it is possible to buy and create a database record")
    @PostMapping (path="/ticket/buy")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    public @ResponseBody void  buyTicket (@RequestBody PassengerDto passengerDto,
                                          @RequestParam Integer scheduleId,
                                          @RequestParam Integer end_station_id,
                                          @RequestParam CharSequence arrivalTime) {
      ticketService.buyTicket(passengerDto, scheduleId, end_station_id, arrivalTime);
    }

    @Operation(summary = "Get ticket by id",
            description = "Finds ticket by id")
    @GetMapping("/ticket/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN', 'CLIENT')")
    Optional<Ticket> findTicketById(@PathVariable Integer id) {
        return ticketService.findTicketById(id);
    }

    @Operation(summary = "Delete ticket by id",
            description = "Delete ticket by id")
    @DeleteMapping("/ticket/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN', 'CLIENT')")
    void deletTicketById(@PathVariable Integer id) {
        ticketService.deletTicketById(id);
    }
}
