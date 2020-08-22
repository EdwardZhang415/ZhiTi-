package com.upic.service;

import com.upic.po.Mission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface MissionService {
    Mission saveMission(Mission mission);

    String deleteMission(long id);

    Page<Mission> searchMission(Pageable pageable);

    List<Mission> searchMission();

    Mission findOne(long id);

    Page<Mission> findByProvingGroundsId(long provingGroundsId, Pageable pageable);

    String updateMission(Mission mission);

    Mission findByMissionNum(String missionNum);

    Mission findByMissionName(String missionName);

    Page<Mission> findByDegreeOfDifficulty(double degreeOfDifficulty,Pageable pageable);

    List<Mission> findByDegreeOfDifficulty(double degreeOfDifficulty);

}
