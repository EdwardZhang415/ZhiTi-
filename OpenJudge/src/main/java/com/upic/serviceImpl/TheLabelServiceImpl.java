package com.upic.serviceImpl;

import com.upic.po.TheLabel;
import com.upic.repository.TheLabelRepository;
import com.upic.service.TheLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TheLabelService")
public class TheLabelServiceImpl implements TheLabelService {
    @Autowired
    private TheLabelRepository theLabelRepository;
    @Override
    public TheLabel saveTheLabel(TheLabel theLabel) {
        return theLabelRepository.save(theLabel);
    }

    @Override
    public String deleteTheLabel(long id) {
        theLabelRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<TheLabel> searchTheLabel(Pageable pageable) {
        return theLabelRepository.findAll(pageable);
    }

    @Override
    public List<TheLabel> searchTheLabel() {
        return theLabelRepository.findAll();
    }

    @Override
    public TheLabel findOne(long id) {
        return theLabelRepository.getOne(id);
    }

    @Override
    public String updateTheLabel(TheLabel theLabel) {
        theLabelRepository.saveAndFlush(theLabel);
        return "SUCCESS";
    }

    @Override
    public TheLabel findByTheLabelName(String theLabelName) {
        return theLabelRepository.findByTheLabelName(theLabelName);
    }
}
