package com.upic.service;

import com.upic.po.ProvingGrounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface ProvingGroundsService {
    ProvingGrounds saveProvingGrounds(ProvingGrounds provingGrounds);

    String deleteProvingGrounds(long id);

    Page<ProvingGrounds> searchProvingGrounds(Pageable pageable);

    List<ProvingGrounds> searchProvingGrounds();

    ProvingGrounds findOne(long id);

    String updateProvingGrounds(ProvingGrounds provingGrounds);

    ProvingGrounds findByCardName(String cardName);
}
