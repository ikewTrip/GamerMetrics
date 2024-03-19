package com.gamermetrics.GamerMetrics.organization;

import com.gamermetrics.GamerMetrics.team.TeamResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationResponse {

    private Integer id;

    private String name;

    private String description;

    private LocalDateTime creationDate;

    private String creatorName;

    private TeamResponse[] teams;

}
