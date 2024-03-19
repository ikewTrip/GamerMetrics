package com.gamermetrics.GamerMetrics.training;

import com.gamermetrics.GamerMetrics.team.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Training {

    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Id
    @GeneratedValue()
    private Integer id;

    private String name;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

}
