package com.upic.repository;

import com.upic.po.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission,Long> {
    Mission findByMissionNum(String missionNum);

    Mission findByMissionName(String missionName);

    Page<Mission> findByDegreeOfDifficulty(double degreeOfDifficulty, Pageable pageable);

    List<Mission> findByDegreeOfDifficulty(double degreeOfDifficulty);

    Page<Mission> findByProvingGroundsId(long provingGroundsId, Pageable pageable);


}
