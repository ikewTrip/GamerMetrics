package com.gamermetrics.GamerMetrics.team;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamRequest {

    @NotBlank(message = "Team name must not be blank")
    @Size(min = 3, max = 20, message = "Team name must be between 3 and 20 characters")
    private String name;

    @NotBlank(message = "Team description must not be blank")
    @Size(min = 3, max = 100, message = "Team description must be between 3 and 100 characters")
    private String description;

}
