package com.gamermetrics.GamerMetrics.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private String nickName;

    private String firstName;

    private String lastName;

    private String email;

    private String steamId;

    private String teamName;

    private String organizationName;

    private String organizationRole;

    private boolean isBlocked;
}
