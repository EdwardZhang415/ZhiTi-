package com.upic.repository;

import com.upic.po.TheLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheLabelRepository extends JpaRepository<TheLabel,Long> {
    TheLabel findByTheLabelName(String theLabelName);
}
