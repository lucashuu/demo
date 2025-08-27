package com.example.demo.userapi.repository;

import com.example.demo.userapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 里面一个方法都不用写！
}
