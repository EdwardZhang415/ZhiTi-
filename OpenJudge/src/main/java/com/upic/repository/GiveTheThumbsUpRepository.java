package com.upic.repository;

import com.upic.po.GiveTheThumbsUp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiveTheThumbsUpRepository extends JpaRepository<GiveTheThumbsUp,Long> {
    Page<GiveTheThumbsUp> findByUserId(long userId, Pageable pageable);

    List<GiveTheThumbsUp> findByUserId(long userId);

    GiveTheThumbsUp findByTheSolutionId(long theSolutionId);
}
