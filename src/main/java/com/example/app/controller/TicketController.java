package com.example.app.controller;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.Ticket;
import com.example.app.repository.TicketRepository;
import com.example.app.service.TicketService;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@RestController
public class TicketController {

    private final TicketRepository repository;
    private TicketService ticketService;

    public TicketController(TicketRepository repository, TicketService ticketService) {
        this.repository = repository;
        this.ticketService = ticketService;
    }
    @GetMapping("/ticket/all")
    Iterable<Ticket> getAllRegisteredPassengers() {
       return ticketService.getAllRegisteredPassengers();
    }

    @PostMapping (path="/ticket/buy")
    public @ResponseBody void  buyTicket (@RequestBody PassengerDto passengerDto,
                                          @RequestParam Integer scheduleId,
                                          @RequestParam Integer end_station_id,
                                          @RequestParam CharSequence arrivalTime) {
      ticketService.buyTicket(passengerDto, scheduleId, end_station_id, arrivalTime);
    }

    @GetMapping("/ticket/{id}")
    Optional<Ticket> findTicketById(@PathVariable Integer id) {
        return ticketService.findTicketById(id);
    }

    @DeleteMapping("/ticket/{id}")
    void deletTicketById(@PathVariable Integer id) {
        ticketService.deletTicketById(id);
    }
}
