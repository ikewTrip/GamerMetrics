package com.gamermetrics.GamerMetrics.metrics;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MetricsRepository extends JpaRepository<Metrics, Integer> {
    void deleteByUserPlay_Id(Integer userPlayId);

    List<Metrics> findAllByUserPlay_Id(Integer userPlayId);
}
