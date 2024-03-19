package com.gamermetrics.GamerMetrics.token;

import com.gamermetrics.GamerMetrics.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
            select t from Token t inner join User u on t.user.id = u.id
            where u.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<Token> findALlValidTokensByUser(Integer userId);

    Optional<Token> findByToken(String token);

    List<Token> findAllByUserId(Integer user_id);
}
