package com.upic.repository;

import com.upic.po.UserThrough;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserThroughRepository extends JpaRepository<UserThrough,Long> {
    Page<UserThrough> findByMissionId(long missionId, Pageable pageable);

    List<UserThrough> findByMissionId(long missionId);

    Page<UserThrough> findByUserId(long userId, Pageable pageable);

    List<UserThrough> findByUserId(long userId);

    Page<UserThrough> findByUserIdAndMissionId(long userId, long missionId, Pageable pageable);

    UserThrough findByUserIdAndMissionId(long userId, long missionId);
}
