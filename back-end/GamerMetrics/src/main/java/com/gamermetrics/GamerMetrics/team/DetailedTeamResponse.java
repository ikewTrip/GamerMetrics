package com.gamermetrics.GamerMetrics.team;

import com.gamermetrics.GamerMetrics.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailedTeamResponse {

    private TeamResponse teamInfo;
    private UserResponse[] teamMembers;
}
