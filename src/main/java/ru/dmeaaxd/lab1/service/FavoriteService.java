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

        Client client = getClientById(clientId);
        Shop shop = getShopById(shopId);

        Favorite favorite = Favorite.builder()
                .client(client)
                .shop(shop)
                .build();

        return favoriteRepository.save(favorite);
    }

    private Client getClientById(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found with id: " + clientId));
    }

    private Shop getShopById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found with id: " + shopId));
    }
}
