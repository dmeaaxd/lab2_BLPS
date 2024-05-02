package ru.dmeaaxd.lab2.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.FavoriteDTO;
import ru.dmeaaxd.lab2.dto.SubscriptionDTO;
import ru.dmeaaxd.lab2.entity.*;
import ru.dmeaaxd.lab2.repository.BillRepository;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;
import ru.dmeaaxd.lab2.repository.SubscriptionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;
    private final ShopRepository shopRepository;
    private final BillRepository billRepository;

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
        Bill bill = client.getAccountBill();


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
        if (bill.getAccountBill() >= totalPrice) {
            bill.setAccountBill(bill.getAccountBill() - totalPrice);
            billRepository.save(bill);
        } else {
            return null;
        }

        clientRepository.save(client);
        subscriptionRepository.save(existingSubscription);


        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .clientId(existingSubscription.getClient().getId())
                .shopId(existingSubscription.getShop().getId())
                .duration(existingSubscription.getDuration())
                .build();

        return subscriptionDTO;
    }


    public List<SubscriptionDTO> getSubscriptions(Long clientId) {
        List<Subscription> subscriptionList = subscriptionRepository.findAllByClientId(clientId);
        List<SubscriptionDTO> subscriptionDTOList = new ArrayList<>();
        for (Subscription subscription : subscriptionList) {
            subscriptionDTOList.add(SubscriptionDTO.builder()
                        .clientId(subscription.getClient().getId())
                        .shopId(subscription.getShop().getId())
                        .build());
        }

        return subscriptionDTOList;
    }
}

