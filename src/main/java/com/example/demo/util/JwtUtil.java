package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

// @Component 注解将这个工具类标记为Spring的一个组件，
// 这样我们就可以在其他地方通过 @Autowired 注入它。
@Component
public class JwtUtil {

    // 这是签发JWT时使用的密钥。在真实项目中，这个值必须非常复杂，
    // 并且不能硬编码在代码里，通常会放在配置文件中。
    // 为了教学方便，我们暂时硬编码在这里。
    // Keys.secretKeyFor() 会根据HS256算法生成一个足够安全的密钥。
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT的有效期，单位是毫秒。这里设置为1小时。
    private final long expiration = 3600_000;

    /**
     * 根据UserDetails生成JWT令牌
     * @param userDetails 包含了用户信息的UserDetails对象
     * @return JWT字符串
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 从JWT令牌中解析出用户名
     * @param token JWT字符串
     * @return 用户名
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 从JWT令牌中解析出过期时间
     * @param token JWT字符串
     * @return 过期时间
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 验证JWT令牌是否有效
     * @param token JWT字符串
     * @param userDetails 用于验证的UserDetails对象
     * @return 如果用户名匹配且令牌未过期，返回true。
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // --- 私有辅助方法 ---

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}