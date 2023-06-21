package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table
@ToString
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column(name = "departure_time")
    LocalDateTime departureTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column(name = "arrival_time")
    LocalDateTime arrivalTime;

    @NotNull
    @Column(name = "places_left")
    Integer places_left;

    public Integer getPlaces_left() {
        return places_left;
    }

    public void setPlaces_left(Integer places_left) {
        this.places_left = places_left;
    }
}
