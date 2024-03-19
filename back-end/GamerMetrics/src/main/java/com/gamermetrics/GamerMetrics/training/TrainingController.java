package com.gamermetrics.GamerMetrics.training;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrainingController {

    private final TrainingService trainingService;

    @GetMapping("/teams/{teamId}/trainings")
    public ResponseEntity<TrainingResponse[]> getTrainings(
            @PathVariable(value = "teamId") Integer teamId,
            Principal principal
    ) {
        return new ResponseEntity<>(trainingService.getTrainingsOfTeam(teamId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/trainings/{trainingId}")
    public ResponseEntity<TrainingResponse> getTraining(
            @PathVariable(value = "trainingId") Integer trainingId,
            Principal principal
    ) {
        return new ResponseEntity<>(trainingService.getTrainingById(trainingId, principal.getName()), HttpStatus.OK);
    }

    @PostMapping("/teams/{teamId}/trainings")
    public ResponseEntity<TrainingResponse> createTraining(
            @PathVariable(value = "teamId") Integer teamId,
            @RequestBody @Valid TrainingRequest trainingRequest,
            Principal principal
    ) {
        return new ResponseEntity<>(trainingService.createTraining(teamId, trainingRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/trainings/{trainingId}")
    public ResponseEntity<TrainingResponse> updateTraining(
            @PathVariable(value = "trainingId") Integer trainingId,
            @RequestBody @Valid TrainingRequest trainingRequest,
            Principal principal
    ) {
        return new ResponseEntity<>(trainingService.updateTraining(trainingId, trainingRequest, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/trainings/{trainingId}")
    public ResponseEntity<Void> deleteTraining(
            @PathVariable(value = "trainingId") Integer trainingId,
            Principal principal
    ) {
        trainingService.deleteTraining(trainingId, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
