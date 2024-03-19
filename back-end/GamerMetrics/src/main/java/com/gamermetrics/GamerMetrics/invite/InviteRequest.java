package com.gamermetrics.GamerMetrics.invite;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteRequest {

    @Size(max = 100, message = "Message must be less than 100 characters")
    private String message;

    @Size(min = 6, max = 7, message = "Organization role name must be PLAYER or TRAINER")
    private String organizationRoleName;

}
