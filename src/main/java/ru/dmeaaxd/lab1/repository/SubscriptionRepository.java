package ru.dmeaaxd.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.entity.Shop;
import ru.dmeaaxd.lab1.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByClientAndShop(Client client, Shop shop);
}
