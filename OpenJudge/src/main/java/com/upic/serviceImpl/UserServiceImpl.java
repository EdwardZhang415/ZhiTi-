package com.upic.serviceImpl;

import com.upic.po.User;
import com.upic.repository.UserRepository;
import com.upic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public String deleteUser(long id) {
        userRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<User> searchUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> searchUser() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public String updateUser(User user) {
        userRepository.saveAndFlush(user);
        return "SUCCESS";
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
}
