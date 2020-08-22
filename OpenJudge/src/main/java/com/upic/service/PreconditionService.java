package com.upic.service;

import com.upic.po.Precondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface PreconditionService {
    Precondition savePrecondition(Precondition precondition);

    String deleteMission(long id);

    Page<Precondition> searchPrecondition(Pageable pageable);

    List<Precondition> searchPrecondition();

    Precondition findOne(long id);

    String updatePrecondition(Precondition precondition);

    Page<Precondition> findByMissionId(long missionId,Pageable pageable);
}
