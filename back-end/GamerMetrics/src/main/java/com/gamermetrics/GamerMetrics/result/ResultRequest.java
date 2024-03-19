package com.gamermetrics.GamerMetrics.result;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultRequest {


    @NotNull(message = "Rounds played is required")
    @Min(value = 13, message = "Rounds played must be greater than 12")
    @Max(value = 30, message = "Rounds played must be less than 31")
    private Integer roundsPlayed;

    @NotNull(message = "Kills is required")
    @Min(value = 0, message = "Kills must be greater than or equal to 0")
    private Integer kills;

    @NotNull(message = "Deaths is required")
    @Min(value = 0, message = "Deaths must be greater than or equal to 0")
    private Integer deaths;

    @NotNull(message = "Assists is required")
    @Min(value = 0, message = "Assists must be greater than or equal to 0")
    private Integer assists;

    @NotNull(message = "Headshots is required")
    @Min(value = 0, message = "Headshots must be greater than or equal to 0")
    private Integer headshots;
}
