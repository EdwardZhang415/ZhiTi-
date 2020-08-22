package com.upic.service;

import com.upic.po.TheLabel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TheLabelService {
    TheLabel saveTheLabel(TheLabel theLabel);

    String deleteTheLabel(long id);

    Page<TheLabel> searchTheLabel(Pageable pageable);

    List<TheLabel> searchTheLabel();

    TheLabel findOne(long id);

    String updateTheLabel(TheLabel theLabel);

    TheLabel findByTheLabelName(String theLabelName);
}
