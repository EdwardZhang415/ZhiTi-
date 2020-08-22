package com.upic.serviceImpl;

import com.upic.po.TheExample;
import com.upic.repository.TheExampleRepository;
import com.upic.service.TheExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TheExampleService")
public class TheExampleServiceImpl implements TheExampleService {
    @Autowired
    private TheExampleRepository theExampleRepository;
    @Override
    public TheExample saveTheExample(TheExample theExample) {
        return theExampleRepository.save(theExample);
    }

    @Override
    public String deleteTheExample(long id) {
        theExampleRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<TheExample> searchTheExample(Pageable pageable) {
        return theExampleRepository.findAll(pageable);
    }

    @Override
    public List<TheExample> searchTheExample() {
        return theExampleRepository.findAll();
    }

    @Override
    public TheExample findOne(long id) {
        return theExampleRepository.getOne(id);
    }

    @Override
    public String updateTheExample(TheExample theExample) {
        theExampleRepository.saveAndFlush(theExample);
        return "SUCCESS";
    }

    @Override
    public List<TheExample> findByQuestionId(long questionId) {
        return theExampleRepository.findByQuestionId(questionId);
    }
}
