package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.CategoryDTO;
import ru.dmeaaxd.lab2.dto.FavoriteDTO;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.entity.Favorite;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.FavoriteRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ClientRepository clientRepository;
    private final ShopRepository shopRepository;

    public Favorite add(Long clientId, Long shopId) throws Exception {
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


    public List<FavoriteDTO> getAll() {
        List<FavoriteDTO> favoriteDTOList = new ArrayList<>();

        for (Favorite favorite : favoriteRepository.findAll()) {
            favoriteDTOList.add(FavoriteDTO.builder()
                    .shopId(favorite.getShop().getId())
                    .build());
        }
        return favoriteDTOList;
    }


    public Long remove(Long favoriteId) throws Exception {
        if (!favoriteRepository.existsById(favoriteId)) {
            throw new Exception("Магазин не найден в избранном");
        }

        favoriteRepository.deleteById(favoriteId);

        return favoriteId;
    }
}
