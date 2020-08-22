package com.upic.serviceImpl;

import com.upic.po.TheSubmit;
import com.upic.repository.TheSubmitRepository;
import com.upic.service.TheSubmitSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TheSubmitSerice")
public class TheSubmitSericeImpl implements TheSubmitSerice {
    @Autowired
    private TheSubmitRepository theSubmitRepository;
    @Override
    public TheSubmit saveTheSubmit(TheSubmit theSubmit) {
        return theSubmitRepository.save(theSubmit);
    }

    @Override
    public String deleteTheSubmit(long id) {
        theSubmitRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<TheSubmit> searchTheSubmit(Pageable pageable) {
        return theSubmitRepository.findAll(pageable);
    }

    @Override
    public List<TheSubmit> searchTheSubmit() {
        return theSubmitRepository.findAll();
    }

    @Override
    public TheSubmit findOne(long id) {
        return theSubmitRepository.getOne(id);
    }

    @Override
    public String updateTheSubmit(TheSubmit theSubmit) {
        theSubmitRepository.saveAndFlush(theSubmit);
        return "SUCCESS";
    }

    @Override
    public Page<TheSubmit> findByUserId(long userId, Pageable pageable) {
        return theSubmitRepository.findByUserId(userId,pageable);
    }

    @Override
    public List<TheSubmit> findByUserId(long userId) {
        return theSubmitRepository.findByUserId(userId);
    }

    @Override
    public Page<TheSubmit> findByQuestionId(long questionId, Pageable pageable) {
        return theSubmitRepository.findByQuestionId(questionId,pageable);
    }

    @Override
    public List<TheSubmit> findByQuestionId(long questionId) {
        return theSubmitRepository.findByQuestionId(questionId);
    }

    @Override
    public List<TheSubmit> findByUserIdAndQuestionId(long userId, long questionId) {
        return theSubmitRepository.findByUserIdAndQuestionId(userId,questionId);
    }

    @Override
    public List<TheSubmit> findByUserIdAndQuestionId(long userId, long questionId, Sort sort) {
        return theSubmitRepository.findByUserIdAndQuestionId(userId,questionId,sort);
    }


}
