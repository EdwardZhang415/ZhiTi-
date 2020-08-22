package com.upic.repository;

import com.upic.enums.QuestionStatusEnum;
import com.upic.po.Mission;
import com.upic.po.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Question findByQuestionName(String questionName);

    Question findByNum(String num);

    Page<Question> findByQuestionBankId(int questionBankId, Pageable pageable);

    List<Question> findByQuestionBankId(int questionBankId);

    Page<Question> findByStatus(QuestionStatusEnum status, Pageable pageable);

    Page<Question> findByQuestionNameLike(String questionName, Pageable pageable);

    Page<Question> findByQuestionNameLikeAndDegreeOfDifficulty(String s, String degreeOfDifficulty, Pageable pageable);
}
