package com.gamermetrics.GamerMetrics.result;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    Optional<Result> findByUserPlay_Id(Integer userPlayId);

    void deleteByUserPlay_Id(Integer userPlayId);
}
