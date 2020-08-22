package com.upic.service;

import com.upic.po.TheSolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TheSolutionService {
    TheSolution saveTheSolution(TheSolution theSolution);

    String deleteTheSolution(long id);

    Page<TheSolution> searchTheSolution(Pageable pageable);

    List<TheSolution> searchTheSolution();

    TheSolution findOne(long id);

    String updateTheSolution(TheSolution theSolution);

    Page<TheSolution> findByQuestionId(long questionId,Pageable pageable);

    List<TheSolution> findByQuestionId(long questionId);

    Page<TheSolution> findByUserId(long userId,Pageable pageable);

    List<TheSolution> findByUserId(long userId);
}
