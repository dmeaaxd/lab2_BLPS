package ru.dmeaaxd.lab2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmeaaxd.lab2.entity.auth.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
