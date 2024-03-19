package com.gamermetrics.GamerMetrics.userplay;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserPlayToUserPlayResponseMapper implements Function<UserPlay, UserPlayResponse> {
    @Override
    public UserPlayResponse apply(UserPlay userPlay) {
        return UserPlayResponse.builder()
                .id(userPlay.getId())
                .name(userPlay.getName())
                .description(userPlay.getDescription())
                .startTime(userPlay.getStartTime().toString())
                .endTime(userPlay.getEndTime() != null ? userPlay.getEndTime().toString() : "in progress")
                .map(userPlay.getMap().toString())
                .build();
    }
}
