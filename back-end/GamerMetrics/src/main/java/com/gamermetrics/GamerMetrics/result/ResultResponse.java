package com.gamermetrics.GamerMetrics.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultResponse {

        private Integer id;

        private Integer roundsPlayed;

        private Integer kills;

        private Integer deaths;

        private Integer assists;

        private Integer headshots;

}
