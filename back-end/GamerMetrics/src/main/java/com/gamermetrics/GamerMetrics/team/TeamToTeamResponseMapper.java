package com.gamermetrics.GamerMetrics.team;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TeamToTeamResponseMapper implements Function<Team, TeamResponse> {
    @Override
    public TeamResponse apply(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .creationDate(team.getCreationDate().toString())
                .organizationName(team.getOrganization().getName())
                .build();
    }
}
