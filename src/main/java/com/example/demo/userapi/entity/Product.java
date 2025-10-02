package com.example.demo.userapi.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

// @Entity 注解表明这是一个JPA实体类，它会映射到数据库中的一张表。
@Entity
// @Table 注解指定了这张表在数据库中的名字。
@Table(name = "products")
public class Product {

    // @Id 注解表明这个字段是表的主键。
    @Id
    // @GeneratedValue 注解配置了主键的生成策略，IDENTITY表示由数据库自动生成（自增）。
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column 注解用于定制列的属性，nullable = false 表示该列不能为空。
    @Column(nullable = false)
    private String name;

    // 使用@Column(columnDefinition = "TEXT")来确保可以存储较长的描述文字。
    @Column(columnDefinition = "TEXT")
    private String description;

    // 对于货币，使用BigDecimal类型是最精确和安全的做法，可以避免浮点数计算误差。
    @Column(nullable = false)
    private BigDecimal price;

    // 这个字段将用于存储上传到S3后的图片公开URL。
    private String imageUrl;

    // --- Getters and Setters ---
    // JPA需要一个无参构造函数
    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }
}