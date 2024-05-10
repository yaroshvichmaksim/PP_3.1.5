package ru.kata.spring.boot_security.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.app.model.User;
import ru.kata.spring.boot_security.demo.app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public User getUser() {
        User user = userService.getUser();
        return user;
    }

}
