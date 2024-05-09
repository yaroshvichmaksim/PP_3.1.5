package ru.kata.spring.boot_security.demo.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.app.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.app.dao.UserRepository;
import ru.kata.spring.boot_security.demo.app.model.Role;
import ru.kata.spring.boot_security.demo.app.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    @Transactional
    @Override
    public void saveUser(User user) {

        if (user.getRoles().isEmpty()) {
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        user.setRoles(user.getRoles().stream()
                .map(role -> roleRepository.findRoleByName(role.getName()))
                .collect(Collectors.toSet()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void saveUser(User user, List<String> roles) {
        Set<Role> role = roleRepository.findByNameIn(roles);
        user.setRoles(role);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUser(User user) {

        User userFromDB = this.getUser(user.getId());

        if (!user.getPassword().equals(userFromDB.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        if (user.getRoles().isEmpty()) {
            user.setRoles(userFromDB.getRoles());
        } else {
            user.setRoles(user.getRoles().stream()
                    .map(role -> roleRepository.findRoleByName(role.getName()))
                    .collect(Collectors.toSet()));
        }
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUser(Long id) {
        User user = null;
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("User Not Found");
        return user;
    }
}
