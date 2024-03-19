package com.gamermetrics.GamerMetrics.result;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ResultToResultResponseMapper implements Function<Result, ResultResponse> {
    @Override
    public ResultResponse apply(Result result) {
        return ResultResponse.builder()
                .id(result.getId())
                .roundsPlayed(result.getRoundsPlayed())
                .kills(result.getKills())
                .deaths(result.getDeaths())
                .assists(result.getAssists())
                .headshots(result.getHeadshots())
                .build();
    }
}
