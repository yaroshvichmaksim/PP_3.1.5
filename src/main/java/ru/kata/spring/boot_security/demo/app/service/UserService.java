package ru.kata.spring.boot_security.demo.app.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.app.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    void saveUser(User user);

//    void updateUser(User user);

    void deleteUser(Long id);

    User getUser(Long id);

    User getUser();

    List<User> getAllUsers();
}
