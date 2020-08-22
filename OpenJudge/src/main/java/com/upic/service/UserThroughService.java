package com.upic.service;
import com.upic.po.UserThrough;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface UserThroughService {
    UserThrough saveUserThrough(UserThrough userThrough);

    String deleteUserThrough(long id);

    Page<UserThrough> searchUserThrough(Pageable pageable);

    List< UserThrough> searchUserThrough();

    UserThrough findOne(long id);

    String updateUserThrough(UserThrough userThrough);

    Page<UserThrough> findByMissionId(long missionId,Pageable pageable);

    List<UserThrough> findByMissionId(long missionId);

    Page<UserThrough> findByUserId(long userId,Pageable pageable);

    List<UserThrough> findByUserId(long userId);

    Page<UserThrough> findByUserIdAndMissionId(long userId,long missionId,Pageable pageable);

    UserThrough findByUserIdAndMissionId(long userId,long missionId);

}
