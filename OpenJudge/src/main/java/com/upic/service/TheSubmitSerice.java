package com.upic.service;

import com.upic.po.TheSubmit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
public interface TheSubmitSerice {
    TheSubmit saveTheSubmit(TheSubmit theSubmit);

    String deleteTheSubmit(long id);

    Page<TheSubmit> searchTheSubmit(Pageable pageable);

    List<TheSubmit> searchTheSubmit();

    TheSubmit findOne(long id);

    String updateTheSubmit(TheSubmit theSubmit);

    Page<TheSubmit> findByUserId(long userId,Pageable pageable);

    List<TheSubmit> findByUserId(long userId);

    Page<TheSubmit> findByQuestionId(long questionId,Pageable pageable);

    List<TheSubmit> findByQuestionId(long questionId);

    List<TheSubmit> findByUserIdAndQuestionId(long userId,long questionId);

    List<TheSubmit> findByUserIdAndQuestionId(long userId,long questionId,Sort sort);
}
