package de.airplanetrackingservice.project.repository;

import de.airplanetrackingservice.project.entity.AirplaneStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AirplaneStatusRepository extends MongoRepository <AirplaneStatus, String> {
    @Query("{icao24:'?0'}")
    List<AirplaneStatus> findFlightStatusByIcao24Code(String icao24);


}
