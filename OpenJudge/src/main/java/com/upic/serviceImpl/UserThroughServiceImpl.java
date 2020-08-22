package com.upic.serviceImpl;

import com.upic.po.UserThrough;
import com.upic.repository.UserThroughRepository;
import com.upic.service.UserThroughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserThroughService")
public class UserThroughServiceImpl implements UserThroughService {
    @Autowired
    private UserThroughRepository userThroughRepository;
    @Override
    public UserThrough saveUserThrough(UserThrough userThrough) {
        return userThroughRepository.save(userThrough);
    }

    @Override
    public String deleteUserThrough(long id) {
        userThroughRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<UserThrough> searchUserThrough(Pageable pageable) {
        return userThroughRepository.findAll(pageable);
    }

    @Override
    public List<UserThrough> searchUserThrough() {
        return userThroughRepository.findAll();
    }

    @Override
    public UserThrough findOne(long id) {
        return userThroughRepository.getOne(id);
    }

    @Override
    public String updateUserThrough(UserThrough userThrough) {
        userThroughRepository.saveAndFlush(userThrough);
        return "SUCCESS";
    }

    @Override
    public Page<UserThrough> findByMissionId(long missionId, Pageable pageable) {
        return userThroughRepository.findByMissionId(missionId,pageable);
    }

    @Override
    public List<UserThrough> findByMissionId(long missionId) {
        return userThroughRepository.findByMissionId(missionId);
    }

    @Override
    public Page<UserThrough> findByUserId(long userId, Pageable pageable) {
        return userThroughRepository.findByUserId(userId,pageable);
    }

    @Override
    public List<UserThrough> findByUserId(long userId) {
        return userThroughRepository.findByUserId(userId);
    }

    @Override
    public Page<UserThrough> findByUserIdAndMissionId(long userId, long missionId,Pageable pageable) {
        return userThroughRepository.findByUserIdAndMissionId(userId,missionId,pageable);
    }

    @Override
    public UserThrough findByUserIdAndMissionId(long userId, long missionId) {
        return userThroughRepository.findByUserIdAndMissionId(userId,missionId);
    }
}
