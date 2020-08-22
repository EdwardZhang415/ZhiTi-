package com.upic.repository;

import com.upic.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    User findByUsername(String username);

    User findByPhone(String phone);
}
