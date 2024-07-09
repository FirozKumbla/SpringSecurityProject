package com.springsecurity.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/admin/home")
    public String handleAdmin() {
        return "admin_home";
    }

    @GetMapping("/user/home")
    public String handleUser() {
        return "user_home";
    }

//    @GetMapping("/login")
//    public String login() {
//        return "custom_login";
//    }
}
