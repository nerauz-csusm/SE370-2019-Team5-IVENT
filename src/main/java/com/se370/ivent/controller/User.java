package com.se370.ivent.controller;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

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

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody com.se370.ivent.models.User postUser(com.se370.ivent.models.User user) {
        String encodedPassword = encoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        return userService.addUser(user);
    }

    @PostMapping(value="/auth", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody com.se370.ivent.models.User authUser(LoginForm loginForm) {
        String encodedPassword = encoder.encode(loginForm.getPassword());

        loginForm.setPassword(encodedPassword);
        return userService.logUser(loginForm);
    }
}
