package ru.kata.spring.boot_security.demo.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.app.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.app.model.User;
import ru.kata.spring.boot_security.demo.app.service.UserService;

import java.util.List;


@Controller
public class AdminController {
    private final UserService userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/admin")
    public String getUser(Model model) {
        model.addAttribute("users", userRepository.getAllUsers());
        model.addAttribute("currentUser", userRepository.getUser());
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "users";
    }

    @PostMapping("/admin/addUser")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "rolesFrom", required = false) List<String> roles) {
        userRepository.saveUser(user, roles);
        return "redirect:/admin";
    }


    @PostMapping("/admin/editUser")
    public String editUser(@ModelAttribute("user") User user, @RequestParam(value = "rolesFromEdit", required = false) List<String> roles) {
        userRepository.updateUser(user, roles);
        return "redirect:/admin";
    }


    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userRepository.deleteUser(id);
        return "redirect:/admin";
    }


}