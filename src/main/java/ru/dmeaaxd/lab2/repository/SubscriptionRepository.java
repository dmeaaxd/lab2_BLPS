package ru.dmeaaxd.lab2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.entity.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByClientAndShop(Client client, Shop shop);

    List<Subscription> findAllByClientId(Long clientId);
}
