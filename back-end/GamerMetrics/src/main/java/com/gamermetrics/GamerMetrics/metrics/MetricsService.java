package com.gamermetrics.GamerMetrics.metrics;

import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.user.UserRepository;
import com.gamermetrics.GamerMetrics.userplay.UserPlay;
import com.gamermetrics.GamerMetrics.userplay.UserPlayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricsService {

    private final MetricsRepository metricsRepository;
    private final UserRepository userRepository;
    private final UserPlayRepository userPlayRepository;

    public MetricsDetailedResponse getMetricsByUserPlayId(Integer userPlayId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        UserPlay userPlay = userPlayRepository.findById(userPlayId)
                .orElseThrow(() -> new IllegalStateException("User play not found"));

        if (!userPlay.getPlayer().getId().equals(user.getId())) {
            throw new IllegalStateException("User play does not belong to user");
        }

        List<Metrics> metrics = metricsRepository.findAllByUserPlay_Id(userPlayId);

        Integer metricsCount = metrics.size();
        Integer heartRateMetricsSum = 0;
        for (Metrics m:
                metrics) {
            heartRateMetricsSum += m.getHeartRate();
        }
        Integer averageHeartRate = heartRateMetricsSum / metricsCount;

        MetricsResponse[] metricsResponses = metrics.stream()
                .map(m -> MetricsResponse.builder()
                        .id(m.getId())
                        .heartRate(m.getHeartRate())
                        .userPlayId(m.getUserPlay().getId())
                        .startMeasuringTime(m.getStartMeasuringTime().toString())
                        .endMeasuringTime(m.getEndMeasuringTime().toString())
                        .build())
                .toArray(MetricsResponse[]::new);

        return new MetricsDetailedResponse(averageHeartRate, metricsResponses);

    }
}
