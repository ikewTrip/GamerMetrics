package com.gamermetrics.GamerMetrics.organization;

import com.gamermetrics.GamerMetrics.team.Team;
import com.gamermetrics.GamerMetrics.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Organization {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private LocalDateTime creationDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "organization")
    private List<User> participants;

    @OneToMany(mappedBy = "organization")
    private List<Team> teams;

}
