package ru.dmeaaxd.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmeaaxd.lab1.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByClientIdAndShopId(Long clientId, Long shopId);
}
