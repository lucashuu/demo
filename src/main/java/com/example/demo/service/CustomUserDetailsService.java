package com.example.demo.service;

import com.example.demo.userapi.entity.User;
import com.example.demo.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

// @Service注解，将这个类标记为Spring的服务层组件
@Service
public class CustomUserDetailsService implements UserDetailsService {

    // 注入我们自己的UserRepository，以便从数据库中查询用户
    @Autowired
    private UserRepository userRepository;

    /**
     * 这是UserDetailsService接口中唯一需要实现的方法。
     * Spring Security会在认证流程中调用此方法，以根据用户名加载用户数据。
     * @param username 用户在登录时输入的用户名
     * @return 一个UserDetails对象，包含了用户的核心信息（用户名、密码、权限等）
     * @throws UsernameNotFoundException 如果根据用户名找不到用户，必须抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 使用UserRepository从数据库中根据用户名查找我们自己的User实体
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. 将我们自己的User实体，转换成Spring Security认识的UserDetails对象。
        // Spring Security提供了一个内置的UserDetails实现类 org.springframework.security.core.userdetails.User，
        // 我们可以方便地使用它。
        // 构造函数需要：用户名、密码、权限列表。
        // 目前我们还没有实现角色权限，所以暂时传入一个空的权限列表。
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // 权限列表，暂时为空
        );
    }
}