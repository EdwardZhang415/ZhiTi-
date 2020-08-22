package com.upic.repository;

import java.util.List;
import com.upic.po.Homework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HomeworkRepository extends JpaRepository<Homework,Long> {

    Homework findByHomeworkName(String homeworkName);

    List<Homework> findByTeamId(long teamId);

    Page<Homework> findByTeamId(long teamId, Pageable pageable);
}
