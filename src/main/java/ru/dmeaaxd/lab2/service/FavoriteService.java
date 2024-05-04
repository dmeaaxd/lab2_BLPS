package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.FavoriteDTO;
import ru.dmeaaxd.lab2.entity.Favorite;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.entity.auth.Client;
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

    public Favorite add(Long shopId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);
        Long clientId = client.getId();

        if (favoriteRepository.existsByClientIdAndShopId(clientId, shopId)) {
            return null;
        }

        Optional<Shop> optionalShop = shopRepository.findById(shopId);
        if (optionalShop.isEmpty()) {
            throw new Exception("Магазин: " + shopId + " не найден");
        }

        Favorite favorite = Favorite.builder()
                .client(client)
                .shop(optionalShop.get())
                .build();

        return favoriteRepository.save(favorite);
    }


    public List<FavoriteDTO> getFavorites() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);

        List<Favorite> favoriteList = favoriteRepository.findAllByClientId(client.getId());
        List<FavoriteDTO> favoriteDTOList = new ArrayList<>();
        for (Favorite favorite : favoriteList) {
            favoriteDTOList.add(FavoriteDTO.builder()
                    .clientId(favorite.getClient().getId())
                    .shopId(favorite.getShop().getId())
                    .build());
        }

        return favoriteDTOList;
    }


    public String remove(Long favoriteId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);

        Favorite favorite = favoriteRepository.findById(favoriteId).orElseThrow(() -> new ObjectNotFoundException(favoriteId, "Магазин"));
        List<Favorite> favoriteList = favoriteRepository.findAllByClientId(client.getId());
        if (!favoriteList.contains(favorite)) {
            throw new Exception("У вас нет прав взаимодействовать с данным магазином");
        }

        favoriteRepository.deleteById(favoriteId);
        return "Магазин удален из избранного";
    }
}
