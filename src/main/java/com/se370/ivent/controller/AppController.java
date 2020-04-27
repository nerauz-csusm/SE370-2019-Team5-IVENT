package com.se370.ivent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @RequestMapping("/")
    public String homePage() {
        return "index";
    }

    @RequestMapping("/bs")
    public String bsPage() {
        return "bs";
    }
}
