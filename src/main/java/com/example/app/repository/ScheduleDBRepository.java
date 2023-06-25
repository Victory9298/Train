package com.example.app.repository;

import com.example.app.entity.Schedule;
import com.example.app.entity.ScheduleDB;
import com.example.app.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleDBRepository extends JpaRepository<ScheduleDB, Integer> {
    @Query(value = "Select new com.example.app.entity.ScheduleDB(sc.id, t.number, s.name, " +
            "sc.departureTime, sc.arrivalTime, sc.placesLeft)" +
            "from  Schedule as sc " +
            "join Station as s on s.id = sc.id " +
            "join Train as t on t.id = sc.id")
    List<ScheduleDB> findAllJpql();

    @Query(value = "Select sc.id, t.number as train_name, s.name as station_name, " +
            "sc.departure_time as departure_time, sc.arrival_time as arrival_time, sc.places_left as places_left " +
            "from  schedule as sc " +
            "join station as s on s.id = sc.station_id " +
            "join train as t on t.id = sc.train_id", nativeQuery = true)
    List<ScheduleDB> findAllNative();

    @Query(value = "Select new com.example.app.entity.Train(t.id, t.number, t.placesNumber)\n" +
            "            from Schedule as sc join Station as s\n" +
            "            on sc.id = s.id\n" +
            "            join Train as t on t.id = sc.id\n" +
            "            WHERE sc.id = :fromStation_id\n" +
            "            AND sc.departureTime > :fromDate" +
            "            AND sc.departureTime < :toDate")
    List<Train> getTrainFromStation(@Param("fromStation_id") Integer toStation_id,
                                    @Param("fromDate") LocalDateTime fromTime,
                                    @Param("toDate") LocalDateTime toDate);

    @Query(value = "Select new com.example.app.entity.Train(t.id, t.number, t.placesNumber)\n" +
            "            from Schedule as sc join Station as s\n" +
            "            on sc.id = s.id\n" +
            "            join Train as t on t.id = sc.id\n" +
            "            WHERE sc.id = :toStation_id\n" +
            "            AND sc.arrivalTime > :fromDate")
    List<Train> getTrainToStation(@Param("toStation_id") Integer toStation_id,
                                  @Param("fromDate") LocalDateTime fromTime);

}
