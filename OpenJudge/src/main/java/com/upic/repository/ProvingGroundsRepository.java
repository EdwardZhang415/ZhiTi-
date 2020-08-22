package com.upic.repository;

import com.upic.po.ProvingGrounds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvingGroundsRepository extends JpaRepository<ProvingGrounds,Long> {
    ProvingGrounds findByCardName(String cardName);
}
