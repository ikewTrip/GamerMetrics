package com.gamermetrics.GamerMetrics.organization;

import com.gamermetrics.GamerMetrics.invite.InviteRepository;
import com.gamermetrics.GamerMetrics.team.Team;
import com.gamermetrics.GamerMetrics.team.TeamRepository;
import com.gamermetrics.GamerMetrics.team.TeamResponse;
import com.gamermetrics.GamerMetrics.team.TeamToTeamResponseMapper;
import com.gamermetrics.GamerMetrics.training.TrainingRepository;
import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;
    private final OrganizationRoleRepository organizationRoleRepository;
    private final TeamRepository teamRepository;
    private final TrainingRepository trainingRepository;
    private final InviteRepository inviteRepository;
    private final TeamToTeamResponseMapper teamToTeamResponseMapper;
    private final OrganizationToOrganizationResponseMapper organizationToOrganizationResponseMapper;

    public OrganizationResponse[] getOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();

        return organizations.stream()
                .map(organization -> OrganizationResponse.builder()
                        .id(organization.getId())
                        .name(organization.getName())
                        .description(organization.getDescription())
                        .creationDate(organization.getCreationDate())
                        .creatorName(getCreatorOfOrganization(organization).getNickName())
                        .build())
                .toArray(OrganizationResponse[]::new);
    }

    public OrganizationResponse getOrganizationById(Integer organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .creationDate(organization.getCreationDate())
                .creatorName(getCreatorOfOrganization(organization).getNickName())
                .teams(organization.getTeams().stream()
                        .map(teamToTeamResponseMapper)
                        .toArray(TeamResponse[]::new))
                .build();
    }

    public OrganizationResponse createOrganization(OrganizationRequest organizationRequest, String email) {
        log.info("In createOrganization method, findByEmail performing");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        if (user.getOrganization() != null) {
            throw new RuntimeException("User is already in organization");
        }

        log.info("In createOrganization method, findByName performing");
        organizationRepository.findByName(organizationRequest.getName())
                .ifPresent(organization -> {
                    throw new RuntimeException("Organization name already taken");
                });

        log.info("In createOrganization method, setOrganizationRoles and findOrganizationRoleByName performing");
        user.setOrganizationRoles(
                Set.of(organizationRoleRepository.findOrganizationRoleByName("CREATOR").get()));

        Organization organization = Organization.builder()
                .name(organizationRequest.getName())
                .description(organizationRequest.getDescription())
                .creationDate(LocalDateTime.now())
                .build();

        log.info("In createOrganization method, setOrganization performing");
        user.setOrganization(organization);

        log.info("In createOrganization method, saveOrganization performing");
        organizationRepository.save(organization);

        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .creationDate(organization.getCreationDate())
                .creatorName(user.getNickName())
                .build();
    }

    public OrganizationResponse updateOrganizationInfo(OrganizationRequest organizationRequest, String email) {
        User user = checkUserIsCreatorOfOrganization(email);

        Organization editedOrganization = user.getOrganization();

        organizationRepository.findAll().stream()
                .filter(org -> org.getName().equals(organizationRequest.getName())
                        && !Objects.equals(org.getId(), editedOrganization.getId()))
                .findFirst()
                .ifPresent(org -> {
                    throw new RuntimeException("Such organization name is already taken!");
                });

        editedOrganization.setName(organizationRequest.getName());
        editedOrganization.setDescription(organizationRequest.getDescription());
        Organization organization = organizationRepository.save(editedOrganization);

        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .creationDate(organization.getCreationDate())
                .creatorName(user.getNickName())
                .build();
    }

    @Transactional
    public void deleteOrganization(String email) {

        User user = checkUserIsCreatorOfOrganization(email);

        Organization organization = user.getOrganization();

        List<Team> organizationTeams = organization.getTeams();

        organizationTeams.forEach(team -> {
                    Team teamToDelete = teamRepository.findById(team.getId())
                            .orElseThrow(() -> new RuntimeException("Team not found"));
                    Integer teamToDeleteId = teamToDelete.getId();

                    trainingRepository.deleteAllByTeam_Id(teamToDeleteId);
                    inviteRepository.deleteAllByTeam_Id(teamToDeleteId);
                    userRepository.findAllByTeam_Id(teamToDeleteId)
                            .forEach(teamUser -> {
                                teamUser.setTeam(null);
                                teamUser.setOrganization(null);
                                teamUser.setOrganizationRoles(null);
                            });
                    teamRepository.delete(team);
                }
        );

        organization.getParticipants().forEach(participant -> {
            participant.setOrganization(null);
            participant.setOrganizationRoles(null);
        });

        organizationRepository.delete(organization);
    }

    public User checkUserIsCreatorOfOrganization(String email) {
        // check if the user is in organization, if not, throw an exception
        User user = checkUserIsInOrganization(email);

        // check if the user is creator of an organization, if not, throw an exception
        if (organizationRoleRepository.findAllByUserSet_Id(user.getId()).stream()
                .noneMatch(organizationRole -> organizationRole.getName().equals("CREATOR"))) {
            throw new RuntimeException("User is not creator of an organization");
        }

        return user;
    }

    public User checkUserIsInOrganization(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check if the user is in organization, if not, throw an exception
        if (user.getOrganization() == null) {
            throw new RuntimeException("User is not in an organization");
        }

        return user;
    }

    private User getCreatorOfOrganization(Organization organization) {
        return organization.getParticipants().stream()
                .filter(participant ->
                        organizationRoleRepository.findAllByUserSet_Id(participant.getId())
                                .stream()
                                .anyMatch(organizationRole -> organizationRole.getName().equals("CREATOR")))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Organization creator not found"));
    }


    public OrganizationRoleResponse getOrganizationRole(String name) {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        OrganizationRole organizationRole = organizationRoleRepository.findAllByUserSet_Id(user.getId()).stream()
                .findFirst().orElseThrow(
                        () -> new RuntimeException("User is not in an organization")
                );

        return OrganizationRoleResponse.builder()
                .id((int) organizationRole.getId())
                .name(organizationRole.getName())
                .build();
    }

    public OrganizationResponse getMyOrganization(String name) {
        User user = userRepository.findByEmail(name)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Organization organization = user.getOrganization();

        return OrganizationResponse.builder()
                .id(organization.getId())
                .name(organization.getName())
                .description(organization.getDescription())
                .creationDate(organization.getCreationDate())
                .creatorName(getCreatorOfOrganization(organization).getNickName())
                .teams(organization.getTeams().stream()
                        .map(teamToTeamResponseMapper)
                        .toArray(TeamResponse[]::new))
                .build();
    }
}
