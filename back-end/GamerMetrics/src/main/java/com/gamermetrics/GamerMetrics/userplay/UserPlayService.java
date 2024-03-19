package com.gamermetrics.GamerMetrics.userplay;

import com.gamermetrics.GamerMetrics.metrics.MetricsIOTAsyncService;
import com.gamermetrics.GamerMetrics.metrics.MetricsRepository;
import com.gamermetrics.GamerMetrics.result.Result;
import com.gamermetrics.GamerMetrics.result.ResultRepository;
import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserPlayService {

    private final UserPlayRepository userPlayRepository;
    private final UserRepository userRepository;
    private final UserPlayToUserPlayResponseMapper userPlayToUserPlayResponseMapper;
    private final ResultRepository resultRepository;
    private final MetricsRepository metricsRepository;
    private final MetricsIOTAsyncService metricsIOTAsyncService;

    public UserPlayResponse getUserPlayById(Integer userPlayId, String name) {
        return userPlayToUserPlayResponseMapper.apply(findAndCheckIsUserPlayBelongsToUser(name, userPlayId));
    }

    public UserPlayResponse[] getUserPlaysByNickName(String nickName) {
        userRepository.findByNickName(nickName)
                .orElseThrow(() -> new IllegalStateException("User nickname not found"));
        return userPlayRepository.findAllByPlayer_NickName(nickName)
                .stream()
                .map(userPlayToUserPlayResponseMapper)
                .toArray(UserPlayResponse[]::new);
    }

    public UserPlayResponse[] getAllUserPlays(String name) {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        return userPlayRepository.findAllByPlayer_Id(user.getId())
                .stream()
                .map(userPlayToUserPlayResponseMapper)
                .toArray(UserPlayResponse[]::new);
    }

    public UserPlayResponse createSoloPlay(UserPlayRequest userPlayRequest, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        // if any user play do not have end time, then throw exception
        if (userPlayRepository.findAllByPlayer_Id(user.getId()).stream()
                .anyMatch(userPlay -> userPlay.getEndTime() == null)) {
            throw new IllegalStateException("User " + user.getNickName() + " has an ongoing play");
        }
        Map map;
        try {
            map = Map.valueOf(userPlayRequest.getMap());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Map name must be one of the following: " +
                    "DE_OVERPASS, DE_ANUBIS, DE_INFERNO, DE_MIRAGE, DE_VERTIGO, DE_NUKE, DE_ANCIENT");
        }
        UserPlay userPlay = UserPlay.builder()
                .name(userPlayRequest.getName())
                .description(userPlayRequest.getDescription())
                .startTime(LocalDateTime.now())
                .map(map)
                .player(user)
                .build();

        metricsIOTAsyncService.receiveAndSaveMetrics(userPlay);

        return userPlayToUserPlayResponseMapper.apply(userPlayRepository.save(userPlay));
    }

    public UserPlayResponse stopPlay(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        UserPlay userPlay = userPlayRepository.findAllByPlayer_Id(user.getId()).stream()
                .filter(u -> u.getEndTime() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("User " + user.getNickName() + " has no ongoing play"));
        if (userPlay.getEndTime() != null) {
            throw new IllegalStateException("User play already stopped");
        }
        userPlay.setEndTime(LocalDateTime.now());
        userPlayRepository.save(userPlay);
        resultRepository.save(
                Result.builder()
                        .roundsPlayed(0)
                        .kills(0)
                        .deaths(0)
                        .assists(0)
                        .headshots(0)
                        .userPlay(userPlay)
                        .build()
        );
        return userPlayToUserPlayResponseMapper.apply(userPlay);
    }

    public UserPlayResponse updateUserPlayById(Integer userPlayId, UserPlayRequest userPlayRequest, String email) {
        UserPlay userPlay = findAndCheckIsUserPlayBelongsToUser(email, userPlayId);

        Map map;
        try {
            map = Map.valueOf(userPlayRequest.getMap());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Map name must be one of the following: " +
                    "DE_OVERPASS, DE_ANUBIS, DE_INFERNO, DE_MIRAGE, DE_VERTIGO, DE_NUKE, DE_ANCIENT");
        }

        userPlay.setName(userPlayRequest.getName());
        userPlay.setDescription(userPlayRequest.getDescription());
        userPlay.setMap(map);

        return userPlayToUserPlayResponseMapper.apply(userPlayRepository.save(userPlay));
    }

    @Transactional
    public void deleteUserPlayById(Integer userPlayId, String email) {
        UserPlay userPlay = findAndCheckIsUserPlayBelongsToUser(email, userPlayId);
        resultRepository.deleteByUserPlay_Id(userPlayId);
        metricsRepository.deleteByUserPlay_Id(userPlayId);
        userPlayRepository.delete(userPlay);
    }

    public Boolean getIfUserPlayAlreadyStarted(String name) {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return userPlayRepository.findAllByPlayer_Id(user.getId()).stream()
                .anyMatch(userPlay -> userPlay.getEndTime() == null);
    }

    private UserPlay findAndCheckIsUserPlayBelongsToUser(String email, Integer userPlayId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        UserPlay userPlay = userPlayRepository.findById(userPlayId)
                .orElseThrow(() -> new IllegalStateException("User play not found"));
        if (!userPlay.getPlayer().getId().equals(user.getId())) {
            throw new IllegalStateException("User " + user.getNickName() + " is not the owner of this play");
        }
        return userPlay;
    }



}
