package ru.dmeaaxd.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmeaaxd.lab2.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
