package com.upic.serviceImpl;

import com.upic.po.Precondition;
import com.upic.repository.PreconditionRepository;
import com.upic.service.PreconditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PreconditionService")
public class PreconditionServiceImpl implements PreconditionService{
    @Autowired
    private PreconditionRepository preconditionRepository;
    @Override
    public Precondition savePrecondition(Precondition precondition) {
        return preconditionRepository.save(precondition);
    }

    @Override
    public String deleteMission(long id) {
        preconditionRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<Precondition> searchPrecondition(Pageable pageable) {
        return preconditionRepository.findAll(pageable);
    }

    @Override
    public List<Precondition> searchPrecondition() {
        return preconditionRepository.findAll();
    }

    @Override
    public Precondition findOne(long id) {
        return preconditionRepository.getOne(id);
    }

    @Override
    public String updatePrecondition(Precondition precondition) {
        preconditionRepository.saveAndFlush(precondition);
        return "SUCCESS";
    }

    @Override
    public Page<Precondition> findByMissionId(long missionId,Pageable pageable) {
        return preconditionRepository.findByMissionId(missionId,pageable);
    }
}
