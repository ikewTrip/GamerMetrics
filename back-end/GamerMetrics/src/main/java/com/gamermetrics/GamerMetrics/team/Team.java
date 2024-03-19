package com.gamermetrics.GamerMetrics.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gamermetrics.GamerMetrics.invite.Invite;
import com.gamermetrics.GamerMetrics.organization.Organization;
import com.gamermetrics.GamerMetrics.training.Training;
import com.gamermetrics.GamerMetrics.user.User;
import com.gamermetrics.GamerMetrics.userplay.UserPlay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "team")
    private List<User> teamMembers;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @OneToMany(mappedBy = "team")
    private List<Training> trainings;

    @OneToMany(mappedBy = "team")
    private List<UserPlay> plays;

    @JsonIgnore
    @OneToMany(mappedBy = "team")
    private List<Invite> invites;

}
