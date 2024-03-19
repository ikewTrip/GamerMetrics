package com.gamermetrics.GamerMetrics.metrics;

import com.gamermetrics.GamerMetrics.userplay.UserPlay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Metrics {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer heartRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_play_id")
    private UserPlay userPlay;

    private LocalDateTime startMeasuringTime;

    private LocalDateTime endMeasuringTime;
}
