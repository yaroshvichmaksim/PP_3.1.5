package ru.kata.spring.boot_security.demo.app.service;

import ru.kata.spring.boot_security.demo.app.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Set<Role> findByNameIn(List<String> roleName);
    Role findRoleByName(String roleName);
    List<Role>findAll();
}
