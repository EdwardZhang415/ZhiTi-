package com.upic.repository;

import com.upic.po.MissionQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionQuestionRepository extends JpaRepository<MissionQuestion,Long> {
    Page<MissionQuestion> findByQuestionId(long questionId, Pageable pageable);

    List<MissionQuestion> findByQuestionId(long questionId);

    Page<MissionQuestion> findByMissionId(long missionId, Pageable pageable);

    List<MissionQuestion> findByMissionId(long missionId);
}
