package com.upic.service;

import com.upic.po.GiveTheThumbsUp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiveTheThumbsUpService {
    GiveTheThumbsUp saveGiveTheThumbsUp(GiveTheThumbsUp giveTheThumbsUp);

    String deleteGiveTheThumbsUp(long id);

    Page<GiveTheThumbsUp> searchGiveTheThumbsUp(Pageable pageable);

    List<GiveTheThumbsUp> searchGiveTheThumbsUp();

    GiveTheThumbsUp findOne(long id);

    String updateGiveTheThumbsUp(GiveTheThumbsUp giveTheThumbsUp);

    GiveTheThumbsUp findByTheSolutionId(long theSolutionId);

    Page<GiveTheThumbsUp>findByUserId(long userId,Pageable pageable);

    List<GiveTheThumbsUp>findByUserId(long userId);
}
