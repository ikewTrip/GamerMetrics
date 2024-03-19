package com.gamermetrics.GamerMetrics.training;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponse {

    private Integer id;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String creationDate;
    private String teamName;

}
