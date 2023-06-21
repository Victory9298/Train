package com.example.app.service;

import com.example.app.entity.ScheduleDB;
import com.example.app.entity.Train;
import com.example.app.repository.ScheduleDBRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ScheduleDBService {

    @Autowired
    private ScheduleDBRepository scheduleDBRepository;

    Logger logger = LoggerFactory.getLogger(ScheduleDBService.class);

    public List<ScheduleDB> getScheduleJpql() {
        return scheduleDBRepository.findAllJpql();
    }

    public List<ScheduleDB> getScheduleNative() {
        return scheduleDBRepository.findAllNative();
    }
    public List<Train> getTrainFromStation(Integer toStation_id,
                                           LocalDateTime fromTime,
                                           LocalDateTime toDate) {
        return scheduleDBRepository.getTrainFromStation(toStation_id, fromTime, toDate);
    }
    public List<Train> getTrainToStation(Integer toStation_id, LocalDateTime fromTime) {
        return scheduleDBRepository.getTrainToStation(toStation_id, fromTime);
    }
}
