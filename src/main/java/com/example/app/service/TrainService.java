package com.example.app.service;

import com.example.app.entity.Train;
import com.example.app.error.ExceptionMessage;
import com.example.app.error.exception.BusinessException;
import com.example.app.repository.TrainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrainService {

    @Autowired
    private TrainRepository repository;

    private Logger logger = LoggerFactory.getLogger(TrainService.class);

    public TrainService() {
    }

    public List<Train> findAllTrains() {
        return repository.findAll();
    }

    public Optional<Train> findTrainById(int id) {

        return Optional.ofNullable(repository.findTrainById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_NOT_FOUND)));
    }
    public Train addNewTrain(Train train) {

        List<Train> trains = repository.findTrainByNumber(train.getNumber());
        if (trains.size() == 0) {
            return repository.save(train);
        } else {
            return trains.get(0);
        }
    }
    public void deleteTrain(int id) {
        findTrainById(id).orElseThrow(() -> new BusinessException(ExceptionMessage.OBJECT_ALREADY_DELETED));
        repository.deleteById(id);
    }

}
