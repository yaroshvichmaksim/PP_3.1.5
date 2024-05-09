package ru.kata.spring.boot_security.demo.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/user")
    public String getUser(Model model) {
        return "user";
    }
    @GetMapping("/admin")
    public String getAdmin(Model model) {
        return "users";
    }
}
