package com.upic.repository;

import com.upic.po.TheSolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheSolutionRepository extends JpaRepository<TheSolution,Long> {
    Page<TheSolution> findByQuestionId(long questionId, Pageable pageable);

    List<TheSolution> findByQuestionId(long questionId);

    Page<TheSolution> findByUserId(long userId, Pageable pageable);

    List<TheSolution> findByUserId(long userId);
}
