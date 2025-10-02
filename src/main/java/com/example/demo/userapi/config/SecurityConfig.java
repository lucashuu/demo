package com.example.demo.userapi.config;

import com.example.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration 注解表明这个类是一个配置类。
// Spring Boot在启动时会自动扫描并加载这些配置。
@Configuration
@EnableWebSecurity // 启用Spring Security的Web安全支持
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // @Bean 注解告诉Spring IoC容器：“请调用这个方法，并将返回的对象实例注册为一个Bean。”
    // 简单来说，就是把这个 PasswordEncoder 对象交由Spring管理，
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 配置AuthenticationManager，告诉Spring Security如何加载用户信息和使用哪个密码加密器
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder()); // 使用我们定义的加密器
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // HttpSecurity 是配置安全规则的核心构建器。
        // 这里的链式调用是一种流畅的API风格，让配置更易读。
        // Spring Security的.hasRole("ADMIN")方法会自动为你添加ROLE_前缀，所以它实际检查的是用户是否拥有ROLE_ADMIN这个权限。
        http
                .csrf(csrf -> csrf.disable()) // 禁用CSRF保护，因为我们用JWT，不需要它
                .authorizeHttpRequests(authz -> authz
                        // 允许所有对 /api/auth/** 路径的请求（用于注册和登录）
                        .requestMatchers("/api/auth/**").permitAll() // <--- 请重点检查这一行！
                        // 其他所有请求都需要认证
                        .anyRequest().authenticated()
                );
        return http.build();
    }



}
