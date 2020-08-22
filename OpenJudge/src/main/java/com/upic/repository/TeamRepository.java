package com.upic.repository;

import com.upic.po.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team,Long> {
    Team findByTeamName(String teamName);

    Team findByUserId(long userId);
}
