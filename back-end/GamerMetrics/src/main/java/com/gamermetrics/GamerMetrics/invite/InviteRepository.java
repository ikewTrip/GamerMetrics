package com.gamermetrics.GamerMetrics.invite;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Integer> {

    void deleteAllByTeam_Id(Integer teamId);

    List<Invite> findAllByUser_Id(Integer user_id);

}
