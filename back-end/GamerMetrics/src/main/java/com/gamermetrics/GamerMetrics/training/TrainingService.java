package com.gamermetrics.GamerMetrics.training;

import com.gamermetrics.GamerMetrics.team.Team;
import com.gamermetrics.GamerMetrics.team.TeamRepository;
import com.gamermetrics.GamerMetrics.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final TeamRepository teamRepository;
    private final TeamService teamService;
    private final TrainingToTrainingResponseMapper trainingToTrainingResponseMapper;

    public TrainingResponse[] getTrainingsOfTeam(Integer teamId, String name) {
        Team team = teamService.checkUserIsInTeam(name, teamId);
        return trainingRepository.findAllByTeam_Id(team.getId()).stream()
                .map(trainingToTrainingResponseMapper)
                .toArray(TrainingResponse[]::new);
    }

    public TrainingResponse getTrainingById(Integer trainingId, String email) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Training not found"));
        teamService.checkUserIsTrainerOfTeam(email, training.getTeam().getId());
        return trainingToTrainingResponseMapper.apply(training);
    }

    public TrainingResponse createTraining(Integer teamId, TrainingRequest trainingRequest, String email) {

        teamService.checkUserIsTrainerOfTeam(email, teamId);

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        LocalDateTime startTime = LocalDateTime.parse(trainingRequest.getStartTime());
        LocalDateTime endTime = LocalDateTime.parse(trainingRequest.getEndTime());

        checkValidityOfTrainingTime(startTime, endTime);

        List<Training> trainingList = trainingRepository.findAllByTeam_Id(teamId);
        if (trainingList.stream()
                .anyMatch(training -> {
                    LocalDateTime thisStartTime = training.getStartTime();
                    LocalDateTime thisEndTime = training.getEndTime();
                    boolean isTimeValid =
                            (thisStartTime.isAfter(startTime) && thisStartTime.isAfter(endTime)) ||
                                    (thisEndTime.isBefore(startTime) && thisEndTime.isBefore(endTime));
                    return !isTimeValid;
                })) {
            throw new RuntimeException("Training overlaps with another training");
        }

        Training training = trainingRepository.save(
                Training.builder()
                        .name(trainingRequest.getName())
                        .description(trainingRequest.getDescription())
                        .startTime(startTime)
                        .endTime(endTime)
                        .creationDate(LocalDateTime.now())
                        .team(team)
                        .build()
        );

        return trainingToTrainingResponseMapper.apply(training);
    }

    public TrainingResponse updateTraining(Integer trainingId, TrainingRequest trainingRequest, String email) {

        Training editedTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Edited training not found"));

        Team team = editedTraining.getTeam();

        teamService.checkUserIsTrainerOfTeam(email, team.getId());

        LocalDateTime startTime = LocalDateTime.parse(trainingRequest.getStartTime());
        LocalDateTime endTime = LocalDateTime.parse(trainingRequest.getEndTime());

        checkValidityOfTrainingTime(startTime, endTime);

        List<Training> trainingList = trainingRepository.findAllByTeam_Id(team.getId());
        if (trainingList.stream()
                .anyMatch(training -> {
                    if (Objects.equals(training.getId(), trainingId)) return false;
                    LocalDateTime thisStartTime = training.getStartTime();
                    LocalDateTime thisEndTime = training.getEndTime();
                    boolean isTimeValid =
                            (thisStartTime.isAfter(startTime) && thisStartTime.isAfter(endTime)) ||
                                    (thisEndTime.isBefore(startTime) && thisEndTime.isBefore(endTime));
                    return !isTimeValid;
                })) {
            throw new RuntimeException("Training overlaps with another training");
        }

        editedTraining.setName(trainingRequest.getName());
        editedTraining.setDescription(trainingRequest.getDescription());
        editedTraining.setStartTime(startTime);
        editedTraining.setEndTime(endTime);

        return trainingToTrainingResponseMapper.apply(trainingRepository.save(editedTraining));
    }

    private void checkValidityOfTrainingTime(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isBefore(LocalDateTime.now()) ||
                startTime.isAfter(endTime) ||
                startTime.isEqual(endTime)) {
            throw new RuntimeException("Start time is after or equal end training time or before now time");
        }
    }

    public void deleteTraining(Integer trainingId, String email) {

        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Training not found"));

        teamService.checkUserIsTrainerOfTeam(email, training.getTeam().getId());

        trainingRepository.delete(training);
    }

}
