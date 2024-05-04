package ru.dmeaaxd.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmeaaxd.lab2.entity.auth.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}

