package com.upic.service;

import com.upic.po.MissionQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface MissionQuestionService {
    MissionQuestion saveMission(MissionQuestion missionQuestion);

    String deleteMissionQuestion(long id);

    Page<MissionQuestion> searchMissionQuestion(Pageable pageable);

    List<MissionQuestion> searchMissionQuestion();

    MissionQuestion findOne(long id);

    Page<MissionQuestion> findByMissionId(long missionId,Pageable pageable);

    List<MissionQuestion> findByMissionId(long missionId);


    String updateMissionQuestion(MissionQuestion missionQuestion);

    Page<MissionQuestion> findByQuestionId(long questionId,Pageable pageable);

    List<MissionQuestion> findByQuestionId(long questionId);


}
