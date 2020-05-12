package com.se370.ivent.controller;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<String> postUser(@RequestBody com.se370.ivent.models.User user) {
        if (userService.addUser(user) == null)
            return ResponseEntity.badRequest().body("Email already taken");
        return ResponseEntity.status(HttpStatus.OK).body("Successfully register");
    }

    @PostMapping(value="/auth")
    public ResponseEntity<String> authUser(@RequestBody LoginForm loginForm) {
        com.se370.ivent.models.User user = userService.logUser(loginForm);

        if (user == null)
            return ResponseEntity.badRequest().body("User not found");

        return ResponseEntity.status(HttpStatus.OK).body("Successfully sign in");
    }
}
