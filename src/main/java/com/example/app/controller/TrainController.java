package com.example.app.controller;
import java.util.List;
import java.util.Optional;

import com.example.app.entity.Train;
import com.example.app.repository.TrainRepository;
import com.example.app.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(description = "Api to manage trains",
        name = "Train Resource")
@SecurityRequirement(name = "Authorization")
public class TrainController {

    private final TrainRepository repository;

    private final TrainService trainService;

    TrainController(TrainRepository repository, TrainService trainService) {
        this.repository = repository;
        this.trainService = trainService;
    }
    @GetMapping("/")
    public String homePage() {
        return "<h2> Welcome to Railway Station Application!";
    }

    @Operation(summary = "Get all trains",
            description = "Provides a list of all trains")
    @GetMapping("/train/all")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN', 'CLIENT')")
    List<Train> all() {
        return trainService.findAllTrains();
    }

    @Operation(summary = "Find train by id",
            description = "Find train by id")
    @GetMapping("/train/{id}")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN', 'CLIENT')")
    public Optional<Train> findTrainById(@PathVariable int id) {
        return trainService.findTrainById(id);
    }

    @Operation(summary = "Add new train",
            description = "Add new train")
    @PostMapping(path="/train")
    @PreAuthorize("hasAnyAuthority('EMPLOYEE', 'ADMIN')")
    public @ResponseBody Train addNewTrain(@RequestBody Train train) {
        return trainService.addNewTrain(train);
    }

    @Operation(summary = "Delete train",
            description = "Delete train")
    @DeleteMapping("/train/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    void deleteTrain(@PathVariable int id) {
        trainService.deleteTrain(id);
    }
}