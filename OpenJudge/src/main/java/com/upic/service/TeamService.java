package com.upic.service;

import com.upic.po.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TeamService {
    Team saveTeam(Team team);

    String deleteTeam(long id);

    Page<Team> searchTeam(Pageable pageable);

    List<Team> searchTeam();

    Team findOne(long id);

    String updateTeam(Team team);

    Team findByTeamName(String teamName);

    Team findByUserId(long userId);
}
