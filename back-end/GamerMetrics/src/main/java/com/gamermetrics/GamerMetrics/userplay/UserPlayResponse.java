package com.gamermetrics.GamerMetrics.userplay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPlayResponse {

    private Integer id;

    private String name;

    private String description;

    private String startTime;

    private String endTime;

    private String map;

}
