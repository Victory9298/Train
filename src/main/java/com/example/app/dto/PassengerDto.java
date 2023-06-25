package com.example.app.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.persistence.NamedStoredProcedureQueries;
import java.time.LocalDateTime;

@Data
@Builder
public class PassengerDto {

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @JsonDeserialize(as = LocalDateTime.class)
    private LocalDateTime birthDate;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public PassengerDto(String name, String surname, LocalDateTime birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }
}
