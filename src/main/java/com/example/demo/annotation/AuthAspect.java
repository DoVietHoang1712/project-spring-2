package com.example.demo.annotation;

import com.example.demo.Constant;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Configuration

public class AuthAspect {
    @Autowired
    AuthorizationImpl authBean;
    @Before("@annotation(com.example.demo.annotation.Authorization) && args(request,..)")
    public void before(HttpServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Request should be HttpServletRequest type");
        }
        if (authBean.authorize(request.getHeader(Constant.HEADER_AUTH))) {

        }
    }
}
