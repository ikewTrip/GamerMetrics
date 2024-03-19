package com.gamermetrics.GamerMetrics.metrics;

import com.gamermetrics.GamerMetrics.userplay.UserPlay;
import com.gamermetrics.GamerMetrics.userplay.UserPlayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetricsIOTAsyncService {

    private static final String IOTDeviceURL = "http://localhost:8180/measure/10";

    private final MetricsRepository metricsRepository;
    private final UserPlayRepository userPlayRepository;

    @Async
    public void receiveAndSaveMetrics(UserPlay userPlay) {
        RestTemplate restTemplate = new RestTemplate();

        while (true) {
            LocalDateTime startMeasuringTime = LocalDateTime.now();
            int heartRate;
            IOTResponse response;
            try {
                response = restTemplate.getForObject(IOTDeviceURL, IOTResponse.class);
            } catch (RestClientException e) {
                throw new IllegalStateException("IOT device is not responding");
            }
            if (response == null) {
                throw new IllegalStateException("IOT device is not working properly");
            }

            heartRate = response.getHeartRate();

            metricsRepository.save(Metrics.builder()
                    .userPlay(userPlay)
                    .heartRate(heartRate)
                    .startMeasuringTime(startMeasuringTime)
                    .endMeasuringTime(LocalDateTime.now())
                    .build());

            UserPlay play = userPlayRepository.findById(userPlay.getId()).get();
            if (play.getEndTime() != null) {
                break;
            }
        }
    }
}