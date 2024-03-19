package com.gamermetrics.GamerMetrics.result;

import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ResultToResultResponseMapper resultToResultResponseMapper;

    public ResultResponse updateResult(ResultRequest resultRequest, Integer userPlayId, Principal principal) {
        Result result = findAndCheckIsResultBelongsToUser(principal.getName(), userPlayId);

        validateResultRequest(resultRequest);

        result.setRoundsPlayed(resultRequest.getRoundsPlayed());
        result.setKills(resultRequest.getKills());
        result.setDeaths(resultRequest.getDeaths());
        result.setAssists(resultRequest.getAssists());
        result.setHeadshots(resultRequest.getHeadshots());

        return resultToResultResponseMapper.apply(resultRepository.save(result));
    }

    public ResultResponse getResult(Integer userPlayId, Principal principal) {
        Result result = findAndCheckIsResultBelongsToUser(principal.getName(), userPlayId);

        return resultToResultResponseMapper.apply(result);
    }

    private Result findAndCheckIsResultBelongsToUser(String email, Integer userPlayId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        Result result = resultRepository.findByUserPlay_Id(userPlayId)
                .orElseThrow(() -> new IllegalStateException("Result not found"));

        user.getUserPlays().stream()
                .filter(userPlay -> userPlay.getId().equals(result.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("It is not your play result"));

        return result;
    }

    private void validateResultRequest(ResultRequest resultRequest) {
        if (resultRequest.getKills() < resultRequest.getHeadshots()) {
            throw new IllegalStateException("Kills must be greater than headshots");
        }
        if (resultRequest.getDeaths() > resultRequest.getRoundsPlayed()) {
            throw new IllegalStateException("Deaths must be less than rounds played");
        }
        if (resultRequest.getKills() > resultRequest.getRoundsPlayed() * 5) {
            throw new IllegalStateException("Enter the actual number of kills");
        }
        if (resultRequest.getAssists() > resultRequest.getRoundsPlayed() * 5) {
            throw new IllegalStateException("Enter the actual number of assists");
        }

    }
}
