package com.se370.ivent.DAO;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDAO {
    @Autowired
    private UserRepository repository;

    public Collection<User> getUsers() {
        return repository.findAll();
    }

    public com.se370.ivent.models.User addUser(com.se370.ivent.models.User user) {
        return repository.insert(user);
    }

    public User logUser(LoginForm loginForm) {
        return repository.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());
    }
}
