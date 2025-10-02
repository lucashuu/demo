package com.example.demo.userapi.config.filter;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
// 继承OncePerRequestFilter确保每个请求只经过此过滤器一次
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 1. 检查Authorization请求头是否存在且格式正确
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // 提取JWT，去掉 "Bearer " 前缀
            username = jwtUtil.extractUsername(jwt);
        }

        // 2. 如果成功提取到用户名，并且当前安全上下文中没有认证信息
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 3. 根据用户名加载用户信息
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 4. 验证令牌是否有效
            if (jwtUtil.validateToken(jwt, userDetails)) {
                // 5. 如果令牌有效，手动创建一个认证令牌
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 6. 将认证令牌设置到安全上下文中，表示当前用户已通过认证
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 7. 无论是否认证成功，都继续执行后续的过滤器链
        chain.doFilter(request, response);
    }
}
