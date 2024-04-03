package ru.dmeaaxd.lab1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab1.dto.ShopDTO;
import ru.dmeaaxd.lab1.entity.Shop;
import ru.dmeaaxd.lab1.repository.ShopRepository;

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

    public Shop getCurrent(Long id) {
        Optional<Shop> shop = shopRepository.findById(id);
        return shop.orElse(null);
    }

}
