package com.gamermetrics.GamerMetrics.metrics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricsResponse {
    private Integer id;
    private Integer heartRate;
    private Integer userPlayId;
    private String startMeasuringTime;
    private String endMeasuringTime;
}
