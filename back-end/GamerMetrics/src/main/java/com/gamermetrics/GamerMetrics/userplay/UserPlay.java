package com.gamermetrics.GamerMetrics.userplay;

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
@Entity(name = "user_play")
public class UserPlay {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Map map;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User player;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
