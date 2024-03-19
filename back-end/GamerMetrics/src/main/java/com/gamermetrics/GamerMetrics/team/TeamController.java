package com.gamermetrics.GamerMetrics.team;

import com.gamermetrics.GamerMetrics.invite.InviteRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/teams")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TeamController {

    private final TeamService teamService;


    @GetMapping("/{teamId}")
    public ResponseEntity<DetailedTeamResponse> getTeamById(Principal principal, @PathVariable Integer teamId) {
        return new ResponseEntity<>(teamService.getTeamById(principal.getName(), teamId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<TeamResponse[]> getAllTeams() {
        return new ResponseEntity<>(teamService.getAllTeams(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@Valid @RequestBody TeamRequest teamRequest, Principal principal) {
        return new ResponseEntity<>(teamService.createTeam(teamRequest, principal.getName()), HttpStatus.CREATED);
    }

    @PostMapping("{teamId}/invite/{nickname}")
    public ResponseEntity<Void> inviteUser(@Valid @RequestBody InviteRequest inviteRequest,
                                        Principal principal, @PathVariable String nickname,
                                        @PathVariable Integer teamId) {
        teamService.inviteUser(inviteRequest, principal.getName(), nickname, teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/remove/{nickname}")
    public ResponseEntity<Void> removeUserFromTeam(Principal principal, @PathVariable String nickname) {
        teamService.removeUserFromTeam(principal.getName(), nickname);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamResponse> updateTeam(Principal principal, @PathVariable Integer teamId, @Valid @RequestBody TeamRequest teamRequest) {
        return new ResponseEntity<>(teamService.updateTeam(principal.getName(), teamId, teamRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<Void> deleteTeam(Principal principal, @PathVariable Integer teamId) {
        teamService.deleteTeam(principal.getName(), teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
