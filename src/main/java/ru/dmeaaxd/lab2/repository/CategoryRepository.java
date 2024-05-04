package ru.dmeaaxd.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmeaaxd.lab2.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
