package com.example.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@ToString
public class ScheduleDto {

    @NotNull
    private Integer stationId;

    @NotNull
    private Integer trainId;

    @NotNull
    private CharSequence arrivalTime;

    @NotNull
    private CharSequence departureTime;

    public Integer getStationId() {
        return stationId;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public LocalDateTime getArrivalTime() {

        return LocalDateTime.parse(arrivalTime, DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss"));
    }

    public LocalDateTime getDepartureTime() {

        return LocalDateTime.parse(departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss"));
    }
}
