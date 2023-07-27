package com.example.app.service;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.app.entity.Train;
import com.example.app.repository.TrainRepository;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {

    @Mock
    private TrainRepository trainRepository;
    @InjectMocks
    private TrainService trainService;

    @Test void findAllTrains()
    {
        trainService.findAllTrains();
        verify(trainRepository).findAll();
    }

    @Test
    void findTrainById() {

        Train train = new Train(1, "A12345", 65);
        Assertions.assertEquals(1, train.getId());
    }

}
