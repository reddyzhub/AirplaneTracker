package de.airplanetrackingservice.project.client;

import de.airplanetrackingservice.project.entity.AirplaneStatus;
import de.airplanetrackingservice.project.repository.AirplaneStatusRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class OpenSkyClient {
    private final RestTemplate restTemplate;
    private final AirplaneStatusRepository airplaneStatusRepository;
    private static final String ARRIVAL_AIRPORT = "EDDF";


    public OpenSkyClient(RestTemplate restTemplate, AirplaneStatusRepository airplaneStatusRepository) {
        this.restTemplate = restTemplate;
        this.airplaneStatusRepository = airplaneStatusRepository;
    }
    @Scheduled(fixedRate = 1*60*60*1000)
    public void getAirplaneTrackingInfo() {
        long beginTime = Instant.now().minus(40, ChronoUnit.HOURS).getEpochSecond();
        long endTime = Instant.now().getEpochSecond();
        String url = String.format("https://opensky-network.org/api/flights/arrival?airport=%s&begin=%s&end=%s", ARRIVAL_AIRPORT, beginTime, endTime);
        System.out.println(url);
        ResponseEntity<List<AirplaneStatus>> responseEntity = this.restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<AirplaneStatus>>() {
        });

        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody());
        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            airplaneStatusRepository.saveAll(responseEntity.getBody());
        } else {
            System.out.println("Error with the Flight Status Request:" + responseEntity.getStatusCode());
        }
    }
}
