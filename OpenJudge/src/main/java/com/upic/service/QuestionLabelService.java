package com.upic.service;
import com.upic.po.QuestionLabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
public interface QuestionLabelService {
    QuestionLabel saveQuestionLabel(QuestionLabel questionLabel);

    String deleteQuestionLabel(long id);

    Page<QuestionLabel> searchQuestion(Pageable pageable);

    List<QuestionLabel> searchQuestion();

    QuestionLabel findOne(long id);

    String updateQuestionLabel(QuestionLabel questionLabel);

    Page<QuestionLabel> findByQuestionId(long questionId,Pageable pageable);

    List<QuestionLabel> findByQuestionId(long questionId);

    Page<QuestionLabel> findByTheLabelId(long theLabelId,Pageable pageable);

    List<QuestionLabel> findByTheLabelId(long theLabelId);

    List<QuestionLabel> findByTheLabelIdIn(Collection<Long> questionIds);
}
