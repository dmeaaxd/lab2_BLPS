package ru.dmeaaxd.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dmeaaxd.lab2.entity.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    public final int PAGE_SIZE = 10;
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
