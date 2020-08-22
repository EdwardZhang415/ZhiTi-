package com.upic.serviceImpl;

import com.upic.po.Mission;
import com.upic.repository.MissionRepository;
import com.upic.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("MissionService")
public class MissionServiceImpl implements MissionService{
    @Autowired
    private MissionRepository missionRepository;
    @Override
    public Mission saveMission(Mission mission) {
        return missionRepository.save(mission);
    }

    @Override
    public String deleteMission(long id) {
        missionRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<Mission> searchMission(Pageable pageable) {
        return missionRepository.findAll(pageable);
    }

    @Override
    public List<Mission> searchMission() {
        return missionRepository.findAll();
    }

    @Override
    public Mission findOne(long id) {
        return missionRepository.getOne(id);
    }

    @Override
    public Page<Mission> findByProvingGroundsId(long provingGroundsId, Pageable pageable) {
        return missionRepository.findByProvingGroundsId(provingGroundsId,pageable);
    }

    @Override
    public String updateMission(Mission mission) {
        missionRepository.saveAndFlush(mission);
        return "SUCCESS";
    }

    @Override
    public Mission findByMissionNum(String missionNum) {
        return missionRepository.findByMissionNum(missionNum);
    }

    @Override
    public Mission findByMissionName(String missionName) {
        return missionRepository.findByMissionName(missionName);
    }

    @Override
    public Page<Mission> findByDegreeOfDifficulty(double degreeOfDifficulty, Pageable pageable) {
        return missionRepository.findByDegreeOfDifficulty(degreeOfDifficulty,pageable);
    }

    @Override
    public List<Mission> findByDegreeOfDifficulty(double degreeOfDifficulty) {
        return missionRepository.findByDegreeOfDifficulty(degreeOfDifficulty);
    }


}
