package com.upic.serviceImpl;

import com.upic.po.TestCase;
import com.upic.repository.TestCaseRepository;
import com.upic.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TestCaseService")
public class TestCaseServiceImpl implements TestCaseService {
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Override
    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    @Override
    public String deleteTestCase(long id) {
        testCaseRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<TestCase> searchTestCase(Pageable pageable) {
        return testCaseRepository.findAll(pageable);
    }

    @Override
    public List<TestCase> searchTestCase() {
        return testCaseRepository.findAll();
    }

    @Override
    public TestCase findOne(long id) {
        return testCaseRepository.getOne(id);
    }

    @Override
    public String updateTestCase(TestCase testCase) {
        testCaseRepository.saveAndFlush(testCase);
        return "SUCCESS";
    }

    @Override
    public TestCase findByTestCaseName(String testCaseName) {
        return testCaseRepository.findByTestCaseName(testCaseName);
    }

    @Override
    public TestCase findByNum(String num) {
        return testCaseRepository.findByNum(num);
    }

    @Override
    public List<TestCase> findByQuestionId(Long questionId) {
        return testCaseRepository.findByQuestionId(questionId);
    }
}
