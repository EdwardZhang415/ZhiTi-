package com.upic.repository;

import com.upic.enums.TeamMembersTypeEnum;
import com.upic.po.TeamMembers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamMembersRepository extends JpaRepository<TeamMembers,Long> {
    TeamMembers findByName(String name);

    TeamMembers findByUserId(long userId);

    Page<TeamMembers> findByTeamId(long teamId, Pageable pageable);

    List<TeamMembers> findByTeamId(long teamId);

    List<TeamMembers> findByTeamIdAndType(Long teamId, TeamMembersTypeEnum type);
}
