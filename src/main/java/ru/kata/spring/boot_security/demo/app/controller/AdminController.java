package ru.kata.spring.boot_security.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.app.model.User;
import ru.kata.spring.boot_security.demo.app.service.UserService;


@Controller
public class AdminController {
    private final UserService userRepository;

    @Autowired
    public AdminController(UserService userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin")
    public String getUser(Model model) {
        model.addAttribute("users", userRepository.getAllUsers());
        return "users";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        userRepository.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "/addUser";
    }

    @PostMapping("/admin/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        userRepository.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/admin/editUser")
    public String editUser(Model model, @RequestParam("id") Long id) {
        User user = userRepository.getUser(id);
        model.addAttribute("user", user);
        return "/updateUser";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userRepository.deleteUser(id);
        return "redirect:/admin";
    }


    @GetMapping("/admin/deleteUser")
    public String deleteUser(Model model, @RequestParam("id") Long id) {
        User user = userRepository.getUser(id);
        model.addAttribute("user", user);
        return "/deleteUser";
    }

}