package com.se370.ivent.DAO;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.models.User;
import kotlin.coroutines.jvm.internal.SuspendFunction;
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

        try {
            if (repository.findByEmail(user.getEmail()) == null)
                return repository.insert(user);
            return null;
        } catch (Exception e) {
            return repository.insert(user);
        }
    }

    public User logUser(LoginForm loginForm) {
        try {
            User user = repository.findByEmail(loginForm.getEmail());
            if (encoder.matches(loginForm.getPassword(), user.getPassword()))
                return  user;
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
