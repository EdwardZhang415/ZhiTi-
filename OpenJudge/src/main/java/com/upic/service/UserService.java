package com.upic.service;

import com.upic.po.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface UserService {
    User saveUser(User user);

    String deleteUser(long id);

    Page<User> searchUser(Pageable pageable);

    List<User> searchUser();

    User findOne(long id);

    String updateUser(User user);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByPhone(String phone);

}
