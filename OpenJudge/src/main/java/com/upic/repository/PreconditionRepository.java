package com.upic.repository;

import com.upic.po.Precondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreconditionRepository extends JpaRepository<Precondition,Long> {
    Page<Precondition> findByMissionId(long missionId, Pageable pageable);
}
