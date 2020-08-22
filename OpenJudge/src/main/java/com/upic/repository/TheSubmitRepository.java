package com.upic.repository;

import com.upic.po.TheSubmit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheSubmitRepository extends JpaRepository<TheSubmit,Long> {
    Page<TheSubmit> findByUserId(long userId, Pageable pageable);

    List<TheSubmit> findByUserId(long userId);

    Page<TheSubmit> findByQuestionId(long questionId, Pageable pageable);

    List<TheSubmit> findByQuestionId(long questionId);

    List<TheSubmit> findByUserIdAndQuestionId(long userId, long questionId);


    List<TheSubmit> findByUserIdAndQuestionId(long userId, long questionId, Sort sort);
}
