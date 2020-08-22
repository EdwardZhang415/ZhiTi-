package com.upic.service;

import com.upic.po.TestCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TestCaseService {
    TestCase saveTestCase(TestCase testCase);

    String deleteTestCase(long id);

    Page<TestCase> searchTestCase(Pageable pageable);

    List<TestCase> searchTestCase();

    TestCase findOne(long id);

    String updateTestCase(TestCase testCase);

    TestCase findByTestCaseName(String testCaseName);

    TestCase findByNum(String num);

    List<TestCase> findByQuestionId(Long questionId);
}
