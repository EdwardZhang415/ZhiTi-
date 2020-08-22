package com.upic.serviceImpl;

import com.upic.enums.QuestionStatusEnum;
import com.upic.po.Question;
import com.upic.repository.QuestionRepository;
import com.upic.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public String deleteQuestion(long id) {
        questionRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<Question> searchQuestion(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public List<Question> searchQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Question findOne(long id) {
        return questionRepository.getOne(id);
    }

    @Override
    public String updateQuestion(Question question) {
        questionRepository.saveAndFlush(question);
        return "SUCCESS";
    }

    @Override
    public Question findByQuestionName(String questionName) {
        return questionRepository.findByQuestionName(questionName);
    }

    @Override
    public Question findByNum(String num) {
        return questionRepository.findByNum(num);
    }

    @Override
    public Page<Question> findByQuestionBankId(int questionBankId,Pageable pageable) {
        return questionRepository.findByQuestionBankId(questionBankId,pageable);
    }

    @Override
    public List<Question> findByQuestionBankId(int questionBankId) {
        return questionRepository.findByQuestionBankId(questionBankId);
    }

    @Override
    public Page<Question> findByStatus(QuestionStatusEnum status,Pageable pageable) {
        return questionRepository.findByStatus(status,pageable);
    }

    @Override
    public Page<Question> findByQuestionNameLike(String questionName, Pageable pageable) {
        return questionRepository.findByQuestionNameLike("%"+questionName+"%",pageable);
    }

    @Override
    public Page<Question> findByQuestionNameLikeAndDegreeOfDifficulty(String questionName, String degreeOfDifficulty, Pageable pageable) {
        return questionRepository.findByQuestionNameLikeAndDegreeOfDifficulty("%"+questionName+"%",degreeOfDifficulty,pageable);
    }

}
