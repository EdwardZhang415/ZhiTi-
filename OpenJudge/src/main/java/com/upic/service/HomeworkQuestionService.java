package com.upic.service;

import com.upic.po.HomeworkQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface HomeworkQuestionService {
    HomeworkQuestion saveHomeworkQuestion(HomeworkQuestion homeworkQuestion);

    String deleteHomeworkQuestion(long id);

    Page<HomeworkQuestion> searchHomeworkQuestion(Pageable pageable);

    List<HomeworkQuestion> searchHomeworkQuestion();

    HomeworkQuestion findOne(long id);

    List<HomeworkQuestion> findByHomeworkId(long homeworkId);

    String updateHomeworkQuestion(HomeworkQuestion homeworkQuestion);

    Page<HomeworkQuestion> findByQuestionId(long questionId,Pageable pageable);

    List<HomeworkQuestion> findByQuestionId(long questionId);
}
