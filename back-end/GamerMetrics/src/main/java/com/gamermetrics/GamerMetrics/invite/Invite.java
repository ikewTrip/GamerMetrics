package com.gamermetrics.GamerMetrics.invite;

import com.gamermetrics.GamerMetrics.organization.OrganizationRole;
import com.gamermetrics.GamerMetrics.team.Team;
import com.gamermetrics.GamerMetrics.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Invite {

    @Id
    @GeneratedValue
    private Integer id;

    private String message;

    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "organization_role_id")
    private OrganizationRole organizationRole;

    boolean isAcceptedByUser;

    boolean isAcceptedByTeam;

}
