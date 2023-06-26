package com.example.app.service;

import com.example.app.entity.Station;
import com.example.app.entity.Train;
import com.example.app.error.ExceptionMessage;
import com.example.app.error.exception.BusinessException;
import com.example.app.repository.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StationService {

    @Autowired
    private StationRepository repository;

    Logger logger = LoggerFactory.getLogger(StationService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public StationService() {}
    public List<Station> findAllStations() {
        return repository.findAll();
    }
    public Optional<Station> findStationById(int id) {
        return repository.findStationById(id);
    }
    public Station addNewStation(Station station) {

        List<Station> stations = repository.findStationByName(station.getName());
        if (stations.size() == 0) {
            return repository.save(station);
        } else {
            Station foundStation = stations.get(0);
            String msg = foundStation.toString() + " already exists ";
            logger.warn(msg);
            kafkaTemplate.send("topic1", msg);

            return foundStation;
        }
    }
    public Optional<Station> findStationById(Integer station_id) {
        return Optional.ofNullable(repository.findStationById(station_id)).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND));
    }

    public void deleteStation(int id) {
        findStationById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        repository.deleteById(id);
    }

}
