package de.airplanetrackingservice.project.controller;

import de.airplanetrackingservice.project.entity.AirplaneStatus;
import de.airplanetrackingservice.project.repository.AirplaneStatusRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AirplaneStatusController {

    private final AirplaneStatusRepository airplaneStatusRepository;

    public AirplaneStatusController(AirplaneStatusRepository airplaneStatusRepository) {
        this.airplaneStatusRepository = airplaneStatusRepository;
    }


    @GetMapping("/airplanestatus/{icao24}")
    public ResponseEntity<List<AirplaneStatus>> getAirplaneStatus(@PathVariable String icao24) {
        return ResponseEntity.ok(airplaneStatusRepository.findFlightStatusByIcao24Code(icao24));
    }

}
