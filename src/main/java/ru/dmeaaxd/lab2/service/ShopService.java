package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.ShopDTO;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

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

    public Shop create(ShopDTO shopDTO) {
        Shop newShop = Shop.builder()
                .name(shopDTO.getName())
                .description(shopDTO.getDescription())
                .build();

        List<Category> categories = new ArrayList<>();
        for (Long categoryId : shopDTO.getCategories()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(categoryId, "Категория"));
            categories.add(category);
        }
        newShop.setCategories(categories);

        return shopRepository.save(newShop);
    }

    public Shop update(Long id, ShopDTO shopDTO) throws ObjectNotFoundException {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));
        shop.setName(shopDTO.getName());
        shop.setDescription(shopDTO.getDescription());

        List<Category> categories = new ArrayList<>();
        for (Long categoryId : shopDTO.getCategories()) {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ObjectNotFoundException(categoryId, "Категория"));
            categories.add(category);
        }
        shop.setCategories(categories);

        return shopRepository.save(shop);
    }

    public void delete(Long id) throws ObjectNotFoundException {
        shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));
        shopRepository.deleteById(id);
    }
}
