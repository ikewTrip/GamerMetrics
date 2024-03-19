package com.gamermetrics.GamerMetrics.team;

import com.gamermetrics.GamerMetrics.invite.Invite;
import com.gamermetrics.GamerMetrics.invite.InviteRepository;
import com.gamermetrics.GamerMetrics.invite.InviteRequest;
import com.gamermetrics.GamerMetrics.organization.OrganizationRole;
import com.gamermetrics.GamerMetrics.organization.OrganizationRoleRepository;
import com.gamermetrics.GamerMetrics.organization.OrganizationService;
import com.gamermetrics.GamerMetrics.training.TrainingRepository;
import com.gamermetrics.GamerMetrics.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final OrganizationRoleRepository organizationRoleRepository;
    private final InviteRepository inviteRepository;
    private final OrganizationService organizationService;
    private final TrainingRepository trainingRepository;
    private final TeamToTeamResponseMapper teamToTeamResponseMapper;
    private final UserToUserResponseMapper userToUserRequestMapper;

    // get all teams of an organization
    public DetailedTeamResponse getTeamById(String name, Integer teamId) {
        User user = organizationService.checkUserIsInOrganization(name);

        // check if the team is in the organization
        Team team = user.getOrganization().getTeams().stream()
                .filter(t -> t.getId().equals(teamId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Team not found"));

        return DetailedTeamResponse.builder()
                .teamInfo(teamToTeamResponseMapper.apply(team))
                .teamMembers(userRepository.findAllByTeam_Id(teamId).stream()
                        .map(userToUserRequestMapper)
                        .toArray(UserResponse[]::new))
                .build();
    }

    // get all teams of an organization
    public TeamResponse[] getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamToTeamResponseMapper)
                .toArray(TeamResponse[]::new);
    }

    // get all teams of an organization
    public TeamResponse createTeam(TeamRequest teamRequest, String email) {

        // check if the user is the creator of the organization
        User user = organizationService.checkUserIsCreatorOfOrganization(email);

        // check if the team name is already taken, if yes, throw an exception
        teamRepository.findByName(teamRequest.getName())
                .ifPresent(team -> {
                    throw new RuntimeException("Team name already taken");
                });

        // create and save a new team
        Team team = Team.builder()
                .name(teamRequest.getName())
                .description(teamRequest.getDescription())
                .creationDate(LocalDateTime.now())
                .build();
        team.setOrganization(user.getOrganization());
        teamRepository.save(team);

        return teamToTeamResponseMapper.apply(team);
    }

    // get all teams of an organization
    public void inviteUser(InviteRequest inviteRequest, String email,
                           String invitedUserNickname, Integer teamId) {
        User creator = organizationService.checkUserIsCreatorOfOrganization(email);

        // check if slots in a team are available, 5 PLAYER slots and 1 TRAINER slot envisioned
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // check if slots in a team are available, 5 PLAYER slots and 1 TRAINER slot envisioned
        List<Long> playersAndTrainersCount = getTeamMembersCounts(team);
        OrganizationRole organizationRole = organizationRoleRepository
                .findOrganizationRoleByName(
                        inviteRequest.getOrganizationRoleName())
                .orElseThrow(() -> new RuntimeException("Organization role not found"));

        if (organizationRole.getName().equals("PLAYER")){
            if (playersAndTrainersCount.get(0) >= 5) throw new RuntimeException("No more slots for players available");
        }
        if (organizationRole.getName().equals("TRAINER")){
            if (playersAndTrainersCount.get(1) >= 1) throw new RuntimeException("No more slots for trainers available");
        }

        // check if the user is already in an organization
        User invitedUser = userRepository.findByNickName(invitedUserNickname)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (invitedUser.getOrganization() != null) {
            throw new RuntimeException("User is already in an organization");
        }
        boolean isUserIsAlreadyInvited = invitedUser.getInvites()
                .stream()
                .anyMatch(invite ->
                        Objects.equals(invite.getTeam().getOrganization().getId(), creator.getOrganization().getId())
                        && !invite.isAcceptedByUser()
                );
        if (isUserIsAlreadyInvited) {
            throw new RuntimeException("User is already invited to an organization");
        }

        // create and save a new invite
        Invite invite = Invite.builder()
                .message(inviteRequest.getMessage())
                .creationDate(LocalDateTime.now())
                .team(
                        teamRepository.findById(teamId)
                                .orElseThrow(() -> new RuntimeException("Team not found")))
                .user(invitedUser)
                .organizationRole(organizationRole)
                .isAcceptedByUser(false)
                .isAcceptedByTeam(true)
                .build();

        inviteRepository.save(invite);
    }

    // get all teams of an organization
    public void removeUserFromTeam(String email, String nickname) {
        // check if the user is the creator of the organization
        organizationService.checkUserIsCreatorOfOrganization(email);

        User userToRemove = userRepository.findByNickName(nickname)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check if the user is in an organization
        organizationService.checkUserIsInOrganization(userToRemove.getEmail());

        userToRemove.setOrganization(null);
        userToRemove.setTeam(null);
        userToRemove.setOrganizationRoles(null);

        userRepository.save(userToRemove);
    }

    // get all teams of an organization
    @Transactional
    public void deleteTeam(String email, Integer teamId) {
        // check if the user is the creator of the organization
        organizationService.checkUserIsCreatorOfOrganization(email);

        // check if the team is in the organization
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // delete all trainings and invites of the team
        trainingRepository.deleteAllByTeam_Id(teamId);
        inviteRepository.deleteAllByTeam_Id(teamId);
        userRepository.findAllByTeam_Id(teamId)
                .forEach(user -> {
                    user.setTeam(null);
                    user.setOrganization(null);
                    user.setOrganizationRoles(null);
                });
        teamRepository.delete(team);
    }

    // get all teams of an organization
    public TeamResponse updateTeam(String name, Integer teamId, TeamRequest teamRequest) {
        User creator = organizationService.checkUserIsCreatorOfOrganization(name);

        // check if the team is in the organization
        Team editedTeam = creator.getOrganization().getTeams().stream()
                .filter(team -> team.getId().equals(teamId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // check if the team name is already taken, if yes, throw an exception
        teamRepository.findByName(teamRequest.getName())
                .ifPresent(team -> {
                    throw new RuntimeException("Team name already taken");
                });

        editedTeam.setName(teamRequest.getName());
        editedTeam.setDescription(teamRequest.getDescription());
        return teamToTeamResponseMapper.apply(teamRepository.save(editedTeam));
    }

    // get all teams of an organization
    public List<Long> getTeamMembersCounts(Team team) {
        // count players and trainers
        AtomicLong playersCount = new AtomicLong();
        AtomicLong trainersCount = new AtomicLong();
        team.getTeamMembers()
                .forEach(teamMember ->
                        organizationRoleRepository.findAllByUserSet_Id(teamMember.getId())
                                .forEach(organizationRole -> {
                                    if (organizationRole.getName().equals("PLAYER")) playersCount.getAndIncrement();
                                    if (organizationRole.getName().equals("TRAINER")) trainersCount.getAndIncrement();
                                })
                );
        return List.of(playersCount.get(), trainersCount.get());
    }

    // get all teams of an organization
    public void checkUserIsTrainerOfTeam(String email, Integer teamId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not authenticated"));
        checkUserIsInTeam(user.getEmail(), teamId);
        // check if the user is a trainer of the team
        if (organizationRoleRepository.findAllByUserSet_Id(user.getId()).stream()
                .noneMatch(organizationRole -> organizationRole.getName().equals("TRAINER"))) {
            throw new RuntimeException("You are not TRAINER of an team");
        }
    }

    // get all teams of an organization
    public Team checkUserIsInTeam(String email, Integer teamId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not authenticated"));
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        // check if the user is in an organization
        if (user.getOrganization() == null) {
            throw new RuntimeException("You are not in an organization");
        }
        if (!Objects.equals(user.getTeam(), team)) {
            throw new RuntimeException("You are not in this team");
        }
        return team;
    }
}

