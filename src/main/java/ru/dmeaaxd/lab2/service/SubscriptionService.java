package ru.dmeaaxd.lab2.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.SubscriptionDTO;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.entity.Subscription;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;
import ru.dmeaaxd.lab2.repository.SubscriptionRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public SubscriptionDTO subscribe(Long clientId, Long shopId, int duration) throws Exception {

        Optional<Client> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isEmpty()) {
            throw new Exception("Клиент: " + clientId + " не найден");
        }


        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isEmpty()) {
            throw new Exception("Магазин: " + shopId + " не найден");
        }

        Client client = optionalClient.get();
        Shop shop = optionalShop.get();


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

//        int totalPrice = duration * 10;
//        if (client.getAccountBill() >= totalPrice) {
//            client.setAccountBill(client.getAccountBill() - totalPrice);
//        } else {
//            return null;
//        }

        clientRepository.save(client);
        subscriptionRepository.save(existingSubscription);


        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setClientId(existingSubscription.getClient().getId());
        subscriptionDTO.setShopId(existingSubscription.getShop().getId());
        subscriptionDTO.setDuration(existingSubscription.getDuration());

        return subscriptionDTO;
    }
}

