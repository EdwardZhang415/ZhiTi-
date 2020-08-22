package com.upic.service;

import com.upic.enums.TeamMembersTypeEnum;
import com.upic.po.TeamMembers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TeamMembersService {
    TeamMembers saveTeamMembers(TeamMembers teamMembers);

    String deleteTeamMembers(long id);

    Page<TeamMembers> searchTeamMembers(Pageable pageable);

    List<TeamMembers> searchTeamMembers();

    TeamMembers findOne(long id);

    String updateTeamMembers(TeamMembers teamMembers);

    TeamMembers findByName(String name);

    TeamMembers findByUserId(long userId);

    Page<TeamMembers> findByTeamId(long teamId,Pageable pageable);

    List<TeamMembers> findByTeamId(long teamId);

    List<TeamMembers> findByTeamIdAndType(Long teamId, TeamMembersTypeEnum type);
}
