package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmeaaxd.lab2.dto.SubscriptionDTO;
import ru.dmeaaxd.lab2.entity.Bill;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.entity.Subscription;
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
    public SubscriptionDTO subscribe(Long shopId, int duration) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);

        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isEmpty()) {
            throw new Exception("Магазин: " + shopId + " не найден");
        }

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

        subscriptionRepository.save(existingSubscription);

        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .clientId(existingSubscription.getClient().getId())
                .shopId(existingSubscription.getShop().getId())
                .duration(existingSubscription.getDuration())
                .build();

        return subscriptionDTO;
    }


    public List<SubscriptionDTO> getSubscriptions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);

        List<Subscription> subscriptionList = subscriptionRepository.findAllByClientId(client.getId());
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

