package com.se370.ivent.DAO;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserDAO {
    @Autowired
    private UserRepository repository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

    public Collection<User> getUsers() {
        return repository.findAll();
    }

    public com.se370.ivent.models.User addUser(com.se370.ivent.models.User user) {
        String encodedPassword = encoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        return repository.insert(user);
    }

    public User logUser(LoginForm loginForm) {
        User user = repository.findByEmail(loginForm.getEmail());
        if (encoder.matches(loginForm.getPassword(), user.getPassword()))
            return  user;
        return null;
    }
}
