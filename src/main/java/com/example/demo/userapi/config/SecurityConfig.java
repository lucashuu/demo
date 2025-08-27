package com.example.demo.userapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// @Configuration 注解表明这个类是一个配置类。
// Spring Boot在启动时会自动扫描并加载这些配置。
@Configuration
public class SecurityConfig {

    // @Bean 注解告诉Spring IoC容器：“请调用这个方法，并将返回的对象实例注册为一个Bean。”
    // 简单来说，就是把这个 PasswordEncoder 对象交由Spring管理，
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // HttpSecurity 是配置安全规则的核心构建器。
        // 这里的链式调用是一种流畅的API风格，让配置更易读。
        http
                .csrf(csrf -> csrf.disable()) // 禁用CSRF保护，因为我们用JWT，不需要它
                .authorizeHttpRequests(authz -> authz
                        // 明确指定哪些路径是公共的，不需要认证
                        .requestMatchers("/api/auth/**").permitAll()
                        // 除了上面允许的路径，其他所有请求都需要认证
                        .anyRequest().authenticated()
                );
        return http.build();
    }

}
