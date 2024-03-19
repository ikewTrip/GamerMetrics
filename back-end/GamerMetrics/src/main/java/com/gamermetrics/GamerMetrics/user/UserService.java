package com.gamermetrics.GamerMetrics.user;

import com.gamermetrics.GamerMetrics.auth.AuthenticationService;
import com.gamermetrics.GamerMetrics.invite.Invite;
import com.gamermetrics.GamerMetrics.invite.InviteRepository;
import com.gamermetrics.GamerMetrics.invite.InviteService;
import com.gamermetrics.GamerMetrics.organization.*;
import com.gamermetrics.GamerMetrics.team.Team;
import com.gamermetrics.GamerMetrics.team.TeamService;
import com.gamermetrics.GamerMetrics.userplay.UserPlayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserToUserResponseMapper userToUserResponseMapper;
    private final AuthenticationService authenticationService;
    private final InviteRepository inviteRepository;
    private final OrganizationRoleRepository organizationRoleRepository;
    private final OrganizationService organizationService;
    private final OrganizationRepository organizationRepository;
    private final TeamService teamService;
    private final InviteService inviteService;
    private final UserPlayRepository userPlayRepository;

    public UserResponse getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userToUserResponseMapper.apply(user);
    }

    public GetAllUsersResponse getAllUsers() {
        return GetAllUsersResponse.builder()
                .users(userRepository.findAll().stream()
                        .filter(user -> !Objects.equals(user.getNickName(), "admin"))
                        .map(userToUserResponseMapper).collect(Collectors.toList()))
                .build();
    }

    public UserResponse getUserByNickName(String nickName) {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userToUserResponseMapper.apply(user);
    }

    public UserResponse updateUser(UserRequest userRequest, String email) {
        if (userRequest.getNickName() == null || userRequest.getNickName().isBlank()) {
            throw new RuntimeException("Nick name cannot be empty");
        }
        Optional<User> userWithRequestedNickName = userRepository.findByNickName(userRequest.getNickName());

        if (userWithRequestedNickName.isPresent() && !userWithRequestedNickName.get().getEmail().equals(email)) {
            throw new RuntimeException("Nick name already taken");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setNickName(userRequest.getNickName());
        user.setSteamId(userRequest.getSteamId());

        userRepository.save(user);

        return userToUserResponseMapper.apply(user);
    }

    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found by email"));
        try {
            organizationService.checkUserIsCreatorOfOrganization(email);
            organizationService.deleteOrganization(email);
            userPlayRepository.deleteAllByPlayer_Id(user.getId());
        } catch (RuntimeException e) {
            userRepository.delete(user);
        }

    }

    public void deleteUserByNickName(String nickName) {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new RuntimeException("User not found by nickname"));
        try {
            organizationService.checkUserIsCreatorOfOrganization(user.getEmail());
            organizationService.deleteOrganization(user.getEmail());
            userRepository.delete(user);
        } catch (RuntimeException e) {
            userRepository.delete(user);
        }
    }

    public void blockUser(String nickName) {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new RuntimeException("User not found by nickname"));
        user.setBlocked(true);
        authenticationService.revokeAllUserTokens(user);
        userRepository.save(user);
    }

    public void unblockUser(String nickName) {
        User user = userRepository.findByNickName(nickName)
                .orElseThrow(() -> new RuntimeException("User not found by nickname"));
        user.setBlocked(false);
        userRepository.save(user);
    }

    public void acceptInvite(Integer id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found by email"));

        Invite invite = user.getInvites().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invite not found"));

        if (invite.isAcceptedByUser() && invite.isAcceptedByTeam()) {
            throw new RuntimeException("Invite is archived");
        }

        Team team = invite.getTeam();

        List<Long> playersAndTrainersCount = teamService.getTeamMembersCounts(team);

        String organizationRole = invite.getOrganizationRole().getName();
        if (organizationRole.equals("PLAYER")){
            if (playersAndTrainersCount.get(0) >= 5) {
                inviteService.archiveInviteById(id);
                throw new RuntimeException("No more slots for players available");
            }
        }
        if (organizationRole.equals("TRAINER")){
            if (playersAndTrainersCount.get(1) >= 1) {
                inviteService.archiveInviteById(id);
                throw new RuntimeException("No more slots for trainers available");
            }
        }

        OrganizationRole suggestedRole = organizationRoleRepository.findOrganizationRoleByName(
                invite.getOrganizationRole().getName()).get();
        user.setOrganizationRoles(Set.of(suggestedRole));

        user.setTeam(team);

        Organization organization = team.getOrganization();
        user.setOrganization(organization);

        organizationRepository.save(organization);

        invite.setAcceptedByUser(true);
        inviteRepository.save(invite);
    }
}