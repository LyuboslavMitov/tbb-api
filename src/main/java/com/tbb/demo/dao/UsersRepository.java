package com.tbb.demo.dao;


import com.tbb.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
}
