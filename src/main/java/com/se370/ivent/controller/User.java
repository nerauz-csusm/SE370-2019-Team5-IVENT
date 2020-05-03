package com.se370.ivent.controller;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class User {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private UserService userService;

    @GetMapping
    public Collection<com.se370.ivent.models.User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping
    public com.se370.ivent.models.User postUser(@RequestBody com.se370.ivent.models.User user) {
        String encodedPassword = encoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        return userService.addUser(user);
    }

    @PostMapping("/auth")
    public com.se370.ivent.models.User authUser(@RequestBody LoginForm loginForm) {
        String encodedPassword = encoder.encode(loginForm.getPassword());

        loginForm.setPassword(encodedPassword);
        return userService.logUser(loginForm);
    }
}
