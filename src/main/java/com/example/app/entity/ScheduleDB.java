package com.example.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ScheduleDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String train_name;

    @Column
    String station_name;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column(name = "departure_time")
    LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss")
    @Column(name = "arrival_time")

    LocalDateTime arrivalTime;

    @NotNull
    @Column(name="places_left")
    Integer placesLeft;

}
