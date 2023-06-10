package com.example.app.service;

import com.example.app.entity.Train;
import com.example.app.repository.TrainRepository;
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
    Train train;
    public TrainService() {
    }
    public List<Train> findAllTrains() {
        return repository.findAll();
    }

    public Optional<Train> findTrainById(int id) {
        return repository.findTrainById(id);
    }
    public Train addNewTrain(Train train) {

        List<Train> trains = repository.findTrainByNumber(train.getNumber());
        if (trains.size() == 0) {
            return repository.save(train);
        } else {
            return trains.get(1);
        }
    }
    public void deleteTrain(int id) {
        repository.deleteById(id);
    }

}
