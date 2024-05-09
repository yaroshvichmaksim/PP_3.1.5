package ru.kata.spring.boot_security.demo.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.app.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.app.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> findByNameIn(List<String> roleName) {
        return roleRepository.findByNameIn(roleName);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
