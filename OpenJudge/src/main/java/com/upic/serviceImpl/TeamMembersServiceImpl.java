package com.upic.serviceImpl;

import com.upic.enums.TeamMembersTypeEnum;
import com.upic.po.TeamMembers;
import com.upic.repository.TeamMembersRepository;
import com.upic.service.TeamMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TeamMembersService")
public class TeamMembersServiceImpl implements TeamMembersService {
    @Autowired
    private TeamMembersRepository teamMembersRepository;
    @Override
    public TeamMembers saveTeamMembers(TeamMembers teamMembers) {
        return teamMembersRepository.save(teamMembers);
    }

    @Override
    public String deleteTeamMembers(long id) {
        teamMembersRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<TeamMembers> searchTeamMembers(Pageable pageable) {
        return teamMembersRepository.findAll(pageable);
    }

    @Override
    public List<TeamMembers> searchTeamMembers() {
        return teamMembersRepository.findAll();
    }

    @Override
    public TeamMembers findOne(long id) {
        return teamMembersRepository.getOne(id);
    }

    @Override
    public String updateTeamMembers(TeamMembers teamMembers) {
        teamMembersRepository.saveAndFlush(teamMembers);
        return "SUCCESS";
    }

    @Override
    public TeamMembers findByName(String name) {
        return teamMembersRepository.findByName(name);
    }

    @Override
    public TeamMembers findByUserId(long userId) {
        return teamMembersRepository.findByUserId(userId);
    }

    @Override
    public Page<TeamMembers> findByTeamId(long teamId, Pageable pageable) {
        return teamMembersRepository.findByTeamId(teamId,pageable);
    }

    @Override
    public List<TeamMembers> findByTeamId(long teamId) {
        return teamMembersRepository.findByTeamId(teamId);
    }

    public  List<TeamMembers> findByTeamIdAndType(Long teamId, TeamMembersTypeEnum type){
        return teamMembersRepository.findByTeamIdAndType(teamId,type);
    }
}
