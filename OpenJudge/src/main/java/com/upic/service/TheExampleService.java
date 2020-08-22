package com.upic.service;

import com.upic.po.TheExample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TheExampleService {
    TheExample saveTheExample(TheExample theExample);

    String deleteTheExample(long id);

    Page<TheExample> searchTheExample(Pageable pageable);

    List<TheExample> searchTheExample();

    TheExample findOne(long id);

    String updateTheExample(TheExample theExample);

    List<TheExample> findByQuestionId(long questionId);
}
