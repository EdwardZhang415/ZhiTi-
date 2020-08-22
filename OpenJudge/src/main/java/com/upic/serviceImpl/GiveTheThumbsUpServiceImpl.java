package com.upic.serviceImpl;

import com.upic.po.GiveTheThumbsUp;
import com.upic.repository.GiveTheThumbsUpRepository;
import com.upic.service.GiveTheThumbsUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GiveTheThumbsUpService")
public class GiveTheThumbsUpServiceImpl implements GiveTheThumbsUpService {
    @Autowired
    private GiveTheThumbsUpRepository giveTheThumbsUpRepository;
    @Override
    public GiveTheThumbsUp saveGiveTheThumbsUp(GiveTheThumbsUp giveTheThumbsUp) {
        return giveTheThumbsUpRepository.save(giveTheThumbsUp);
    }

    @Override
    public String deleteGiveTheThumbsUp(long id) {
        giveTheThumbsUpRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<GiveTheThumbsUp> searchGiveTheThumbsUp(Pageable pageable) {
        return giveTheThumbsUpRepository.findAll(pageable);
    }

    @Override
    public List<GiveTheThumbsUp> searchGiveTheThumbsUp() {
        return giveTheThumbsUpRepository.findAll();
    }

    @Override
    public GiveTheThumbsUp findOne(long id) {
        return giveTheThumbsUpRepository.getOne(id);
    }

    @Override
    public String updateGiveTheThumbsUp(GiveTheThumbsUp giveTheThumbsUp) {
        giveTheThumbsUpRepository.saveAndFlush(giveTheThumbsUp);
        return "SUCCESS";
    }

    @Override
    public GiveTheThumbsUp findByTheSolutionId(long theSolutionId) {
        return giveTheThumbsUpRepository.findByTheSolutionId(theSolutionId);
    }

    @Override
    public Page<GiveTheThumbsUp> findByUserId(long userId, Pageable pageable) {
        return giveTheThumbsUpRepository.findByUserId(userId,pageable);
    }

    @Override
    public List<GiveTheThumbsUp> findByUserId(long userId) {
        return giveTheThumbsUpRepository.findByUserId(userId);
    }


}
