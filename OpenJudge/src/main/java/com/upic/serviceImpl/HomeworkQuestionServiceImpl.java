package com.upic.serviceImpl;

import com.upic.po.HomeworkQuestion;
import com.upic.repository.HomeworkQuestionRepository;
import com.upic.service.HomeworkQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("HomeworkQuestionService")
public class HomeworkQuestionServiceImpl implements HomeworkQuestionService {
    @Autowired
    private HomeworkQuestionRepository homeworkQuestionRepository;
    @Override
    public HomeworkQuestion saveHomeworkQuestion(HomeworkQuestion homeworkQuestion) {
        return homeworkQuestionRepository.save(homeworkQuestion);
    }

    @Override
    public String deleteHomeworkQuestion(long id) {
        homeworkQuestionRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<HomeworkQuestion> searchHomeworkQuestion(Pageable pageable) {
        return homeworkQuestionRepository.findAll(pageable);
    }

    @Override
    public List<HomeworkQuestion> searchHomeworkQuestion() {
        return homeworkQuestionRepository.findAll();
    }

    @Override
    public HomeworkQuestion findOne(long id) {
        return homeworkQuestionRepository.getOne(id);
    }

    @Override
    public List<HomeworkQuestion> findByHomeworkId(long homeworkId) {
        return homeworkQuestionRepository.findByHomeworkId(homeworkId);
    }

    @Override
    public String updateHomeworkQuestion(HomeworkQuestion homeworkQuestion) {
        homeworkQuestionRepository.saveAndFlush(homeworkQuestion);
        return "SUCCESS";
    }

    @Override
    public Page<HomeworkQuestion> findByQuestionId(long questionId, Pageable pageable) {
        return homeworkQuestionRepository.findByQuestionId(questionId,pageable);
    }

    @Override
    public List<HomeworkQuestion> findByQuestionId(long questionId) {
        return homeworkQuestionRepository.findByQuestionId(questionId);
    }
}
