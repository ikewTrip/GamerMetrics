package com.gamermetrics.GamerMetrics.config;

import com.gamermetrics.GamerMetrics.organization.OrganizationRole;
import com.gamermetrics.GamerMetrics.organization.OrganizationRoleRepository;
import com.gamermetrics.GamerMetrics.user.Role;
import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StartUpSeeder {

    private final OrganizationRoleRepository organizationRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void run() {
        if (organizationRoleRepository.count() > 0) {
            return;
        }
        seedOrganizationRoles();
        seedAdminUser();
    }

    private void seedAdminUser() {
        User admin =  User.builder()
                .nickName("admin")
                .firstName("admin")
                .lastName("admin")
                .email("admin@admin")
                .password(passwordEncoder.encode("adminadmin"))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
    }

    private void seedOrganizationRoles() {
        OrganizationRole roleCreator = OrganizationRole.builder()
                .name("CREATOR")
                .build();

        OrganizationRole rolePlayer = OrganizationRole.builder()
                .name("PLAYER")
                .build();

        OrganizationRole roleTrainer = OrganizationRole.builder()
                .name("TRAINER")
                .build();

        organizationRoleRepository.saveAll(List.of(roleCreator, rolePlayer, roleTrainer));
    }
}
