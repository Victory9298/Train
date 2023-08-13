package com.example.app.controller;

import com.example.app.entity.Station;
import com.example.app.repository.StationRepository;
import com.example.app.service.StationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(description = "Api to manage stations",
        name = "Stations Resource")
public class StationController {
    private final StationRepository repository;
    private final StationService stationService;

    StationController(StationRepository repository, StationService stationService) {
        this.repository = repository;
        this.stationService = stationService;
    }

    @Operation(summary = "Get all stations",
            description = "Provides a list of all stations")
    @GetMapping("/station/all")
    List<Station> all() {
        return stationService.findAllStations();
    }

    @Operation(summary = "Get station by id",
            description = "Finds station by id")
    @GetMapping("/station/{id}")
    public Optional<Station> findStationById(@PathVariable int id) {
        return stationService.findStationById(id);
    }

    @Operation(summary = "Add new station",
            description = "Adds new station")
    @PostMapping(path="/station")
    public @ResponseBody Station addNewStation(@RequestBody Station station) {
        return stationService.addNewStation(station);
    }

    @Operation(summary = "Delete station",
            description = "Deletes station found by id")
    @DeleteMapping("/station/{id}")
    void deleteStation(@PathVariable int id) {
        stationService.deleteStation(id);
    }
}
