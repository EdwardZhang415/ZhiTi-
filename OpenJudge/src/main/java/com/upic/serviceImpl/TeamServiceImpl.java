package com.upic.serviceImpl;

import com.upic.po.Team;
import com.upic.repository.TeamRepository;
import com.upic.service.TeamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TeamService")
public class TeamServiceImpl implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Override
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public String deleteTeam(long id) {
        teamRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<Team> searchTeam(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }

    @Override
    public List<Team> searchTeam() {
        return teamRepository.findAll();
    }

    @Override
    public Team findOne(long id) {
        return teamRepository.getOne(id);
    }

    @Override
    public String updateTeam(Team team) {
        teamRepository.saveAndFlush(team);
        return "SUCCESS";
    }

    @Override
    public Team findByTeamName(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    @Override
    public Team findByUserId(long userId) {
        return teamRepository.findByUserId(userId);
    }
}
