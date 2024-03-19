package com.gamermetrics.GamerMetrics.userplay;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface UserPlayRepository extends JpaRepository<UserPlay, Integer> {

    List<UserPlay> findAllByPlayer_Id(Integer player_id);

    List<UserPlay> findAllByPlayer_NickName(String nickName);

    void deleteAllByPlayer_Id(Integer player_id);

}
