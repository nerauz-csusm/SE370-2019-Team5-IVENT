package com.se370.ivent.service;

import com.se370.ivent.DAO.UserDAO;
import com.se370.ivent.models.LoginForm;
import com.se370.ivent.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Collection<User> getUsers() {
        return userDAO.getUsers();
    }

    public com.se370.ivent.models.User addUser(com.se370.ivent.models.User user) {
        return userDAO.addUser(user);
    }

    public User logUser(LoginForm loginForm) {
        return userDAO.logUser(loginForm);
    }
}
