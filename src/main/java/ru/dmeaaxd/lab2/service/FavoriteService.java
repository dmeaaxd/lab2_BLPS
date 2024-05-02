package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.entity.Favorite;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.FavoriteRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ClientRepository clientRepository;
    private final ShopRepository shopRepository;

    public Favorite addToFavorite(Long clientId, Long shopId) throws Exception {
        if (favoriteRepository.existsByClientIdAndShopId(clientId, shopId)) {
            return null;
        }

        Optional<Client> optionalClient = clientRepository.findById(clientId);

        if (optionalClient.isEmpty()) {
            throw new Exception("Клиент: " + clientId + " не найден");
        }


        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isEmpty()) {
            throw new Exception("Магазин: " + shopId + " не найден");
        }


        Favorite favorite = Favorite.builder()
                .client(optionalClient.get())
                .shop(optionalShop.get())
                .build();

        return favoriteRepository.save(favorite);
    }
}
