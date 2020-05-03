package com.se370.ivent.controller;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/user")
public class User {
    @Autowired
    private UserService userService;

    @GetMapping
    public Collection<com.se370.ivent.models.User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody com.se370.ivent.models.User postUser(com.se370.ivent.models.User user) {
        return userService.addUser(user);
    }

    @PostMapping(value="/auth", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public @ResponseBody com.se370.ivent.models.User authUser(LoginForm loginForm) {
        return userService.logUser(loginForm);
    }
}
