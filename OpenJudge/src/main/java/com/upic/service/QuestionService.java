package com.upic.service;

import com.upic.enums.QuestionStatusEnum;
import com.upic.po.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface QuestionService {
    Question saveQuestion(Question question);

    String deleteQuestion(long id);

    Page<Question> searchQuestion(Pageable pageable);

    List<Question> searchQuestion();

    Question findOne(long id);

    String updateQuestion(Question question);

    Question findByQuestionName(String questionName);

    Question findByNum(String num);

    Page<Question> findByQuestionBankId(int questionBankId,Pageable pageable);

    List<Question> findByQuestionBankId(int questionBankId);

    Page<Question> findByStatus(QuestionStatusEnum status,Pageable pageable);

    Page<Question> findByQuestionNameLike(String questionName,Pageable pageable);

    Page<Question> findByQuestionNameLikeAndDegreeOfDifficulty(String questionName,String degreeOfDifficulty,Pageable pageable);
}
