package ru.kata.spring.boot_security.demo.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.app.model.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findByNameIn(List<String> roleName);
    Role findRoleByName(String roleName);
    List<Role> findAll();
}
