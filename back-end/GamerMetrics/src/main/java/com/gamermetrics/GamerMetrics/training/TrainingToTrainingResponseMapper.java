package com.gamermetrics.GamerMetrics.training;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TrainingToTrainingResponseMapper implements Function<Training, TrainingResponse> {

    @Override
    public TrainingResponse apply(Training training) {
        return TrainingResponse.builder()
                .id(training.getId())
                .name(training.getName())
                .description(training.getDescription())
                .startTime(training.getStartTime().toString())
                .endTime(training.getEndTime().toString())
                .creationDate(training.getCreationDate().toString())
                .teamName(training.getTeam().getName())
                .build();
    }
}
