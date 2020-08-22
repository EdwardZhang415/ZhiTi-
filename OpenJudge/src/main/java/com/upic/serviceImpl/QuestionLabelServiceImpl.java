package com.upic.serviceImpl;

import com.upic.po.QuestionLabel;
import com.upic.repository.QuestionLabelRepository;
import com.upic.service.QuestionLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service("QuestionLabelService")
public class QuestionLabelServiceImpl implements QuestionLabelService {
    @Autowired
    private QuestionLabelRepository questionLabelRepository;
    @Override
    public QuestionLabel saveQuestionLabel(QuestionLabel questionLabel) {
        return questionLabelRepository.save(questionLabel);
    }

    @Override
    public String deleteQuestionLabel(long id) {
        questionLabelRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<QuestionLabel> searchQuestion(Pageable pageable) {
        return questionLabelRepository.findAll(pageable);
    }

    @Override
    public List<QuestionLabel> searchQuestion() {
        return questionLabelRepository.findAll();
    }

    @Override
    public QuestionLabel findOne(long id) {
        return questionLabelRepository.getOne(id);
    }

    @Override
    public String updateQuestionLabel(QuestionLabel questionLabel) {
        questionLabelRepository.saveAndFlush(questionLabel);
        return "SUCCESS";
    }

    @Override
    public Page<QuestionLabel> findByQuestionId(long questionId, Pageable pageable) {
        return questionLabelRepository.findByQuestionId(questionId,pageable);
    }

    @Override
    public List<QuestionLabel> findByQuestionId(long questionId) {
        return questionLabelRepository.findByQuestionId(questionId);
    }

    @Override
    public Page<QuestionLabel> findByTheLabelId(long theLabelId, Pageable pageable) {
        return questionLabelRepository.findByTheLabelId(theLabelId,pageable);
    }

    @Override
    public List<QuestionLabel> findByTheLabelId(long theLabelId) {
        return questionLabelRepository.findByTheLabelId(theLabelId);
    }

    @Override
    public List<QuestionLabel> findByTheLabelIdIn(Collection<Long> questionIds) {
        return questionLabelRepository.findByTheLabelIdIn(questionIds);
    }
}
