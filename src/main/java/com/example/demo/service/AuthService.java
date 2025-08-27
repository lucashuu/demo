package com.example.demo.service;

import com.example.demo.userapi.entity.User;
import com.example.demo.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        // 举例: 用户提交的密码是 "password123"
        String rawPassword = user.getPassword();
        // 经过加密后，变成类似 "$2a$10$..." 的一长串字符串
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // 我们在数据库里存储的是加密后的密码
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    // 登录逻辑我们稍后在集成JWT时再完善
}
