package com.gamermetrics.GamerMetrics.invite;

import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {

    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;

    public InviteResponse[] getActiveInvites(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Invite> invites = inviteRepository.findAllByUser_Id(user.getId());

        return invites.stream()
                .filter(i -> !i.isAcceptedByUser)
                .map(i -> InviteResponse.builder()
                        .id(i.getId())
                        .message(i.getMessage())
                        .teamName(i.getTeam().getName())
                        .organizationRoleName(i.getOrganizationRole().getName())
                        .creationDate(i.getCreationDate())
                        .build())
                .toArray(InviteResponse[]::new);
    }

    public void archiveInviteById(Integer id) {
        Invite invite = inviteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invite not found"));
        invite.setAcceptedByUser(true);
        invite.setAcceptedByTeam(true);
        inviteRepository.save(invite);
    }

}
