package com.example.demo.userapi.repository;

import com.example.demo.userapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 这就是查询派生（Query Derivation）的魔力。
     * 你不需要写任何SQL语句，只需要按照“findBy<字段名>”的格式定义方法，
     * Spring Data JPA就会在运行时自动为你生成查询。
     * "findByUsername" 会被自动翻译成 "SELECT * FROM users WHERE username = ?"
     * 使用Optional<User>作为返回类型，是一种更安全的做法，可以避免空指针异常。
     * @param username 要查询的用户名
     * @return 一个包含User对象的Optional，如果找不到则为空
     */
    Optional<User> findByUsername(String username);
}
