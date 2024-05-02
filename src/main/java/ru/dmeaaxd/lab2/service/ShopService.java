package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.ShopDTO;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public Shop create(ShopDTO shopDTO) {
        Shop newShop = Shop.builder()
                .name(shopDTO.getName())
                .description(shopDTO.getDescription())
                .cashbackPercent(shopDTO.getCashbackPercent())
                .build();
        return shopRepository.save(newShop);
    }

    public List<Shop> getAll() {
        return shopRepository.findAll();
    }

    public Shop getCurrent(Long id) throws Exception {
        Optional<Shop> shop = shopRepository.findById(id);

        if (!shop.isPresent()) {
            throw new Exception("Такого магазина нет");
        }

        return shop.get();
    }

}
