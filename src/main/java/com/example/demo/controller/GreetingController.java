package com.example.demo.controller;

import com.example.demo.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    // 在这里贴了一张 @Autowired 的“零件申请单”
    // Spring会自动把GreetingService的实例“注入”到这个字段里
    @Autowired
    private GreetingService greetingService;


    @GetMapping("/greeting")
    public String greeting() {
        // 直接使用由Spring提供的service实例
        return greetingService.getGreeting();
    }
}


