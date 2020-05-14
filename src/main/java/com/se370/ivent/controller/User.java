package com.se370.ivent.controller;

import com.se370.ivent.models.LoginForm;
import com.se370.ivent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> authUser(@RequestBody LoginForm loginForm) {
        com.se370.ivent.models.User user = userService.logUser(loginForm);
        Map<String, Object> response = new HashMap<String, Object>();

        if (user == null) {
            response.put("message", "Invalid email or password");
            return ResponseEntity.badRequest().body(response);
        }

        response.put("message", "Successfully logged in");
        response.put("data", user);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
