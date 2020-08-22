package com.upic.repository;

import com.upic.po.HomeworkQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HomeworkQuestionRepository extends JpaRepository<HomeworkQuestion,Long> {
    List<HomeworkQuestion> findByQuestionId(long questionId);

    Page<HomeworkQuestion> findByQuestionId(long questionId, Pageable pageable);

    List<HomeworkQuestion> findByHomeworkId(long homeworkId);
}
