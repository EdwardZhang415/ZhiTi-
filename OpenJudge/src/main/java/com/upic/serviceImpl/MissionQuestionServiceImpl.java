package com.upic.serviceImpl;

import com.upic.po.MissionQuestion;
import com.upic.repository.MissionQuestionRepository;
import com.upic.service.MissionQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("MissionQuestion")
public class MissionQuestionServiceImpl implements MissionQuestionService {
    @Autowired
    private MissionQuestionRepository missionQuestionRepository;
    @Override
    public MissionQuestion saveMission(MissionQuestion missionQuestion) {
        return missionQuestionRepository.save(missionQuestion);
    }

    @Override
    public String deleteMissionQuestion(long id) {
        missionQuestionRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<MissionQuestion> searchMissionQuestion(Pageable pageable) {
        return missionQuestionRepository.findAll(pageable);
    }

    @Override
    public List<MissionQuestion> searchMissionQuestion() {
        return missionQuestionRepository.findAll();
    }

    @Override
    public MissionQuestion findOne(long id) {
        return missionQuestionRepository.getOne(id);
    }

    @Override
    public Page<MissionQuestion> findByMissionId(long missionId,Pageable pageable) {
        return missionQuestionRepository.findByMissionId(missionId,pageable);
    }

    @Override
    public List<MissionQuestion> findByMissionId(long missionId) {
        return missionQuestionRepository.findByMissionId(missionId);
    }

    @Override
    public String updateMissionQuestion(MissionQuestion missionQuestion) {
        missionQuestionRepository.saveAndFlush(missionQuestion);
        return "SUCCESS";
    }

    @Override
    public Page<MissionQuestion> findByQuestionId(long questionId, Pageable pageable) {
        return missionQuestionRepository.findByQuestionId(questionId,pageable);
    }

    @Override
    public List<MissionQuestion> findByQuestionId(long questionId) {
        return missionQuestionRepository.findByQuestionId(questionId);
    }
}
