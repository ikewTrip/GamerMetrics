package com.gamermetrics.GamerMetrics.user;

import com.gamermetrics.GamerMetrics.organization.OrganizationRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserToUserResponseMapper implements Function<User, UserResponse> {

    private final OrganizationRoleRepository organizationRoleRepository;

    @Override
    public UserResponse apply(User user) {
        UserResponse.UserResponseBuilder userResponseBuilder = UserResponse.builder()
                .nickName(user.getNickName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .steamId(user.getSteamId())
                .isBlocked(user.isBlocked());

        if (Objects.nonNull(user.getTeam())) {
            userResponseBuilder.teamName(user.getTeam().getName());
        }

        if (Objects.nonNull(user.getOrganization())) {
            userResponseBuilder.organizationName(user.getOrganization().getName());
        }

        if (Objects.nonNull(user.getOrganization())) {
            userResponseBuilder.organizationRole(
                    organizationRoleRepository.findAllByUserSet_Id(user.getId()).get(0).getName()
            );
        }

        return userResponseBuilder.build();
    }
}
