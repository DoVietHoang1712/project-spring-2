package com.example.demo.controller;

import com.example.demo.annotation.Authorization;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserRepository userRepository;

    @GetMapping(value = "/user")
    @Authorization
    public String getUser() {
        return "123";
    }
}
