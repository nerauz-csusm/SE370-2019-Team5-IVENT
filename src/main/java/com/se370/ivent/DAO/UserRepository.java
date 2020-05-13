package com.se370.ivent.DAO;

import com.se370.ivent.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByEmail(String email);
}