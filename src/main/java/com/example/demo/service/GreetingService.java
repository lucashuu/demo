package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.time.LocalTime;

// @Service标签，声明这是一个“业务处理车间”
@Service
public class GreetingService {
    public String getGreeting() {
        int hour = LocalTime.now().getHour();
        if (hour < 12) {
            return "上午好！";
        } else if (hour < 18) {
            return "下午好！";
        } else {
            return "晚上好！";
        }
    }
}
