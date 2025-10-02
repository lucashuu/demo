package com.example.demo.userapi.repository;

import com.example.demo.userapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 目前不需要自定义查询方法
}
