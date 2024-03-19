package com.gamermetrics.GamerMetrics.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {

    private Integer id;

    private String name;

    private String description;

    private String creationDate;

    private String organizationName;

}
