package ru.dmeaaxd.lab1.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab1.dto.SubscriptionDTO;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.entity.Shop;
import ru.dmeaaxd.lab1.entity.Subscription;
import ru.dmeaaxd.lab1.repository.ClientRepository;
import ru.dmeaaxd.lab1.repository.ShopRepository;
import ru.dmeaaxd.lab1.repository.SubscriptionRepository;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public SubscriptionDTO subscribe(Long clientId, Long shopId, int duration) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Клиент: " + clientId + " не найден"));

        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Магазин: " + shopId + " не найден"));

        Subscription existingSubscription = subscriptionRepository.findByClientAndShop(client, shop);
        if (existingSubscription != null) {
            existingSubscription.setDuration(existingSubscription.getDuration() + duration);
        } else {
            existingSubscription = Subscription.builder()
                    .client(client)
                    .shop(shop)
                    .duration(duration)
                    .build();
        }

        int totalPrice = duration * 10;
        if (client.getAccountBill() >= totalPrice) {
            client.setAccountBill(client.getAccountBill() - totalPrice);
        } else {
            return null;
        }

        clientRepository.save(client);
        subscriptionRepository.save(existingSubscription);


        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setClientId(existingSubscription.getClient().getId());
        subscriptionDTO.setShopId(existingSubscription.getShop().getId());
        subscriptionDTO.setDuration(existingSubscription.getDuration());

        return subscriptionDTO;
    }
}

