package com.upic.service;

import com.upic.po.Homework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HomeworkService {
    Homework saveHomework(Homework homework);

    String deleteHomework(long id);

    Page<Homework> searchHomework(Pageable pageable);

    List<Homework> searchHomework();

    Homework findOne(long id);

    List<Homework> findByTeamId(long teamId);

    Page<Homework> findByTeamId(long teamId, Pageable pageable);

    String updateHomework(Homework homework);

    Homework findByHomeworkName(String homeworkName);
}
