package com.gamermetrics.GamerMetrics.invite;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InviteResponse {

    private Integer id;

    private String message;

    private String teamName;

    private String organizationRoleName;

    private LocalDateTime creationDate;

}
