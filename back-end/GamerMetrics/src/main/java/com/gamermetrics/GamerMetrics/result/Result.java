package com.gamermetrics.GamerMetrics.result;

import com.gamermetrics.GamerMetrics.userplay.UserPlay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Result {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer roundsPlayed;

    private Integer kills;

    private Integer deaths;

    private Integer assists;

    private Integer headshots;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_play_id")
    private UserPlay userPlay;

}
