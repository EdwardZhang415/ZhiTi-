package com.upic.repository;

import com.upic.po.QuestionLabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface QuestionLabelRepository extends JpaRepository<QuestionLabel,Long> {
    Page<QuestionLabel> findByQuestionId(long questionId, Pageable pageable);

    List<QuestionLabel> findByQuestionId(long questionId);

    Page<QuestionLabel> findByTheLabelId(long theLabelId, Pageable pageable);

    List<QuestionLabel> findByTheLabelId(long theLabelId);

    List<QuestionLabel> findByTheLabelIdIn(Collection<Long> questionIds);
}
