package ru.dmeaaxd.lab1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.entity.Favorite;
import ru.dmeaaxd.lab1.entity.Shop;
import ru.dmeaaxd.lab1.repository.ClientRepository;
import ru.dmeaaxd.lab1.repository.FavoriteRepository;
import ru.dmeaaxd.lab1.repository.ShopRepository;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ClientRepository clientRepository;
    private final ShopRepository shopRepository;

    public Favorite addToFavorite(Long clientId, Long shopId) {
        if (favoriteRepository.existsByClientIdAndShopId(clientId, shopId)) {
            return null;
        }

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Клиент: " + clientId + " не найден"));
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Магазин: " + shopId + " не найден"));

        Favorite favorite = Favorite.builder()
                .client(client)
                .shop(shop)
                .build();

        return favoriteRepository.save(favorite);
    }
}
