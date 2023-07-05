package com.example.app.kafka;

import com.example.app.aspect.StationServiceAspect;
import com.example.app.dto.PassengerDto;
import com.example.app.entity.Passenger;
import com.example.app.service.PassengerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConsumer {

    @Autowired
    public PassengerService passengerService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "customers",
            groupId = "test-consumer-group")


    public void
    consume(String message) throws JsonProcessingException {

        String msg = "message = " + message;
        logger.info(msg);
        kafkaTemplate.send("topic1", msg);
        Map<String, String> map = new HashMap<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(message, Map.class);
        } catch (Exception e) {
            String errorMsg = e.getMessage();
            logger.error(errorMsg);
            kafkaTemplate.send("topic1", errorMsg);
        }
        String name = (String) map.get("name");
        String surname = (String) map.get("surname");
        CharSequence birthDateString = map.get("clientBirthDate") + "@00:00:00";
        LocalDateTime birtDate = LocalDateTime.parse(birthDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss"));
        PassengerDto passengerDto = new PassengerDto(name, surname, birtDate);
        Passenger passenger = passengerService.addNewPassenger(passengerDto);
    }
}
