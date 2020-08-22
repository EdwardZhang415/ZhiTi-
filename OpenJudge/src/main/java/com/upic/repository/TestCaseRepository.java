package com.upic.repository;

import com.upic.po.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase,Long> {
    TestCase findByTestCaseName(String testCaseName);

    TestCase findByNum(String num);

    List<TestCase> findByQuestionId(Long questionId);
}
