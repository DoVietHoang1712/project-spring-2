package com.example.demo.annotation;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationImpl {
    public boolean authorize(String token) {
        return true;
    }
}
