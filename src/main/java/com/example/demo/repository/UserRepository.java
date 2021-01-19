package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

@Configuration
public interface UserRepository extends MongoRepository<User, Integer> {

}
