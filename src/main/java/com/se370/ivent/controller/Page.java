package com.se370.ivent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Page {

    @RequestMapping("/")
    public String homePage() { return "index"; }

    @RequestMapping("/login")
    public String signinPage() { return "login"; }

    @RequestMapping("/register")
    public String registerPage() {
        return "register";
    }
}
