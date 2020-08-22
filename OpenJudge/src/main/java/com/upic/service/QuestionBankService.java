package com.upic.service;

import com.upic.enums.QuestionBankTypeEnum;
import com.upic.po.QuestionBank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface QuestionBankService {
    QuestionBank saveQuestionBank(QuestionBank questionBank);

    String deleteQuestionBank(long id);

    Page<QuestionBank> searchQuestionBank(Pageable pageable);

    List<QuestionBank> searchQuestionBank();

    QuestionBank findOne(long id);

    String updateQuestionBank(QuestionBank questionBank);

    QuestionBank findByQuestionBankName(String questionBankName);

    Page<QuestionBank> findByType(QuestionBankTypeEnum type,Pageable pageable);

    List<QuestionBank> findByType(QuestionBankTypeEnum type);

    Page<QuestionBank> findByFollowId(long followId,Pageable pageable);

    List<QuestionBank> findByFollowId(long followId);
}
