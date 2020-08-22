package com.upic.repository;

import com.upic.enums.QuestionBankTypeEnum;
import com.upic.po.QuestionBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionBankRepository extends JpaRepository<QuestionBank,Long> {
    QuestionBank findByQuestionBankName(String questionBankName);

    Page<QuestionBank> findByType(QuestionBankTypeEnum type, Pageable pageable);

    List<QuestionBank> findByType(QuestionBankTypeEnum type);

    Page<QuestionBank> findByFollowId(long followId, Pageable pageable);

    List<QuestionBank> findByFollowId(long followId);
}
