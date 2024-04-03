package ru.dmeaaxd.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmeaaxd.lab1.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
}
