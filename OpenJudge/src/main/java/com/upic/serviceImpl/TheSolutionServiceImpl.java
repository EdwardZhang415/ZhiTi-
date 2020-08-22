package com.upic.serviceImpl;

import com.upic.po.TheSolution;
import com.upic.repository.TheSolutionRepository;
import com.upic.service.TheSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TheSolutionService")
public class TheSolutionServiceImpl implements TheSolutionService {
    @Autowired
    private TheSolutionRepository theSolutionRepository;
    @Override
    public TheSolution saveTheSolution(TheSolution theSolution) {
        return theSolutionRepository.save(theSolution);
    }

    @Override
    public String deleteTheSolution(long id) {
        theSolutionRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<TheSolution> searchTheSolution(Pageable pageable) {
        return theSolutionRepository.findAll(pageable);
    }

    @Override
    public List<TheSolution> searchTheSolution() {
        return theSolutionRepository.findAll();
    }

    @Override
    public TheSolution findOne(long id) {
        return theSolutionRepository.getOne(id);
    }

    @Override
    public String updateTheSolution(TheSolution theSolution) {
        theSolutionRepository.saveAndFlush(theSolution);
        return "SUCCESS";
    }

    @Override
    public Page<TheSolution> findByQuestionId(long questionId, Pageable pageable) {
        return theSolutionRepository.findByQuestionId(questionId,pageable);
    }

    @Override
    public List<TheSolution> findByQuestionId(long questionId) {
        return theSolutionRepository.findByQuestionId(questionId);
    }

    @Override
    public Page<TheSolution> findByUserId(long userId, Pageable pageable) {
        return theSolutionRepository.findByUserId(userId,pageable);
    }

    @Override
    public List<TheSolution> findByUserId(long userId) {
        return theSolutionRepository.findByUserId(userId);
    }
}
