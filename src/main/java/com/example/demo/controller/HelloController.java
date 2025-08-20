package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import com.example.demo.dto.Message;
import com.example.demo.userapi.entity.User;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Success! I am a Java developer, and this is my first web API!";
    }

    @GetMapping("/user/{username}")
    public String getUserProfile(@PathVariable String username) {
        return "Displaying profile for user: " + username;
    }

    @GetMapping("/api/message")
    public Message getJsonResponse() {
        return new Message("This is my first JSON response from Spring Boot!");
    }

    @PostMapping("/api/messages")
    public Message createMessage(@RequestBody Message incomingMessage) {
        String reply = "We have received your message." + incomingMessage.getContent();
        return new Message(reply);
    }

    @PostMapping("/")
    public Message createUser(@RequestBody User user) {
        String reply = "User" + user.getUsername() + "was created with email" + user.getEmail();
        return new Message(reply);
    }


}
