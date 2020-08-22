package com.upic.serviceImpl;

import com.upic.po.ProvingGrounds;
import com.upic.repository.ProvingGroundsRepository;
import com.upic.service.ProvingGroundsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProvingGroundsService")
public class ProvingGroundsServiceImpl implements ProvingGroundsService{
    @Autowired
    private ProvingGroundsRepository provingGroundsRepository;
    @Override
    public ProvingGrounds saveProvingGrounds(ProvingGrounds provingGrounds) {
        return provingGroundsRepository.save(provingGrounds);
    }

    @Override
    public String deleteProvingGrounds(long id) {
        provingGroundsRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<ProvingGrounds> searchProvingGrounds(Pageable pageable) {
        return provingGroundsRepository.findAll(pageable);
    }

    @Override
    public List<ProvingGrounds> searchProvingGrounds() {
        return provingGroundsRepository.findAll();
    }

    @Override
    public ProvingGrounds findOne(long id) {
        return provingGroundsRepository.getOne(id);
    }

    @Override
    public String updateProvingGrounds(ProvingGrounds provingGrounds) {
        provingGroundsRepository.saveAndFlush(provingGrounds);
        return "SUCCESS";
    }

    @Override
    public ProvingGrounds findByCardName(String cardName) {
        return provingGroundsRepository.findByCardName(cardName);
    }
}
