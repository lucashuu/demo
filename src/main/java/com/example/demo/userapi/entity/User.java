package com.example.demo.userapi.entity;

import jakarta.persistence.*;

@Entity // 告诉JPA：这个类是一个实体，需要被映射到数据库
@Table(name = "users") // 指定映射到数据库中的表名叫 "users"
public class User {

    @Id // 声明这个字段是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 告诉数据库，这个ID由数据库自动生成（自增）
    private Long id;

    @Column(nullable = false, unique = true) // 定义列的属性：不能为空，且值必须唯一
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    // 为了JPA能够创建对象，需要一个无参构造函数
    public User() {}

    // ...保留你原来的构造函数、getter和setter...
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    // ... setters
}
