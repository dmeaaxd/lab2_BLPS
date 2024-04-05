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
