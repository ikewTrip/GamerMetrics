package com.gamermetrics.GamerMetrics.training;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Integer> {

    List<Training> findAllByTeam_Id(Integer teamId);

    void deleteAllByTeam_Id(Integer teamId);
}
