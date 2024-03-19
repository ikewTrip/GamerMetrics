package com.gamermetrics.GamerMetrics.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickName(String nickName);

    List<User> findAllByTeam_Id(Integer teamId);
}
