package com.upic.repository;

import com.upic.po.TheExample;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TheExampleRepository extends JpaRepository<TheExample,Long> {
    List<TheExample> findByQuestionId(long questionId);
}
