package com.upic.serviceImpl;

import com.upic.enums.QuestionBankTypeEnum;
import com.upic.po.QuestionBank;
import com.upic.repository.QuestionBankRepository;
import com.upic.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("QuestionBankService")
public class QuestionBankServiceImpl implements QuestionBankService {
    @Autowired
    private QuestionBankRepository questionBankRepository;
    @Override
    public QuestionBank saveQuestionBank(QuestionBank questionBank) {
        return questionBankRepository.save(questionBank);
    }

    @Override
    public String deleteQuestionBank(long id) {
        questionBankRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<QuestionBank> searchQuestionBank(Pageable pageable) {
        return questionBankRepository.findAll(pageable);
    }

    @Override
    public List<QuestionBank> searchQuestionBank() {
        return questionBankRepository.findAll();
    }

    @Override
    public QuestionBank findOne(long id) {
        return questionBankRepository.getOne(id);
    }

    @Override
    public String updateQuestionBank(QuestionBank questionBank) {
        questionBankRepository.saveAndFlush(questionBank);
        return "SUCCESS";
    }

    @Override
    public QuestionBank findByQuestionBankName(String questionBankName) {
        return questionBankRepository.findByQuestionBankName(questionBankName);
    }

    @Override
    public Page<QuestionBank> findByType(QuestionBankTypeEnum type, Pageable pageable) {
        return questionBankRepository.findByType(type,pageable);
    }

    @Override
    public List<QuestionBank> findByType(QuestionBankTypeEnum type) {
        return questionBankRepository.findByType(type);
    }

    @Override
    public Page<QuestionBank> findByFollowId(long followId, Pageable pageable) {
        return questionBankRepository.findByFollowId(followId,pageable);
    }

    @Override
    public List<QuestionBank> findByFollowId(long followId) {
        return questionBankRepository.findByFollowId(followId);
    }


}
