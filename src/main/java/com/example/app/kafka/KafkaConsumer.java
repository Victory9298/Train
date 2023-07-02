package com.example.app.kafka;

import com.example.app.dto.PassengerDto;
import com.example.app.entity.Passenger;
import com.example.app.service.PassengerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class KafkaConsumer {

    @Autowired
    public PassengerService passengerService;

    @KafkaListener(topics = "customers",
            groupId = "test-consumer-group")

    // Method
    public void
    consume(String message) throws JsonProcessingException {
        // Print statement
        System.out.println("message = " + message);

        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = mapper.readValue(message, Map.class);
        String name = (String) map.get("name");
        String surname = (String) map.get("surname");
        PassengerDto passengerDto = new PassengerDto(name, surname, LocalDateTime.now());
        Passenger passenger = passengerService.addNewPassenger(passengerDto);
    }
}
