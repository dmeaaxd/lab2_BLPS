package ru.dmeaaxd.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmeaaxd.lab2.entity.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
