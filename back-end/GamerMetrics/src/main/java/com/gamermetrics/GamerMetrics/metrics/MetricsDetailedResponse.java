package com.gamermetrics.GamerMetrics.metrics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetricsDetailedResponse {
    private Integer averageHeartRate;
    private MetricsResponse[] metrics;
}
