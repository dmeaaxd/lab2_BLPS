package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmeaaxd.lab2.dto.DiscountDTO;
import ru.dmeaaxd.lab2.dto.category.CategoryDTORequest;
import ru.dmeaaxd.lab2.dto.shop.ShopDTO;
import ru.dmeaaxd.lab2.dto.shop.ShopFilters;
import ru.dmeaaxd.lab2.dto.shop.ShopGetAllViewDTO;
import ru.dmeaaxd.lab2.dto.shop.ShopGetCurrentViewDTO;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.entity.Discount;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.repository.CategoryRepository;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.DiscountRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;
import ru.dmeaaxd.lab2.utils.ShopComparator;
import ru.dmeaaxd.lab2.utils.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final ClientRepository clientRepository;
    private final DiscountRepository discountRepository;
    private final CategoryRepository categoryRepository;



    public List<ShopGetAllViewDTO> getAll(ShopFilters filters) {
        List<ShopGetAllViewDTO> shopGetAllViewDTOList = new ArrayList<>();
        List<Shop> shops = shopRepository.findAll();

        // Обработка фильтра page
        int min_index = 0;
        int max_index = shops.size();
        if (filters.getPage() != -1){
            min_index = shopRepository.PAGE_SIZE * (filters.getPage() - 1);
            max_index = Math.min(shopRepository.PAGE_SIZE * filters.getPage(), shops.size());
        }

        // Обработка фильтра categories
        List<Category> allowCategories = new ArrayList<>();
        if (!filters.getCategories().isEmpty()) {
            for (int categoryId : filters.getCategories()) {
                allowCategories.add(categoryRepository.findById((long) categoryId).orElseThrow(() -> new ObjectNotFoundException(categoryId, "Категория")));
            }
        }

        // Фильтрация магазинов
        List<Shop> filteredShops = new ArrayList<>();
        for (int i = min_index; i < max_index; i++){
            Shop shop = shops.get(i);
            if (!allowCategories.isEmpty() && !allowCategories.contains(shop.getCategory())){
                continue;
            }
            filteredShops.add(shop);
        }

        // Сортировка магазинов
        if (filters.getSort() == Sort.ASC){
            filteredShops.sort(new ShopComparator());
        }
        else {
            if (filters.getSort() == Sort.DESC) {
                filteredShops.sort(new ShopComparator());
                Collections.reverse(filteredShops);
            }
        }

        // Вывод в виде DTO
        for (Shop shop : filteredShops){
            Category category = shop.getCategory();

            CategoryDTORequest categoryDTORequest = CategoryDTORequest.builder()
                    .name(category.getName())
                    .build();

            shopGetAllViewDTOList.add(ShopGetAllViewDTO.builder()
                    .id(shop.getId())
                    .name(shop.getName())
                    .description(shop.getDescription())
                    .category(categoryDTORequest)
                    .build());
        }
        return shopGetAllViewDTOList;
    }

    public ShopGetCurrentViewDTO getCurrent(Long id) throws Exception {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        List<Discount> discounts = shop.getDiscounts();
        List<DiscountDTO> discountDTOList = new ArrayList<>();
        for (Discount discount : discounts) {
            discountDTOList.add(DiscountDTO.builder()
                    .title(discount.getTitle())
                    .description(discount.getDescription())
                    .promoCode(discount.getPromoCode())
                    .build());
        }

        Category category = shop.getCategory();
        CategoryDTORequest categoryDTORequest = CategoryDTORequest.builder()
                .name(category.getName())
                .build();

        return ShopGetCurrentViewDTO.builder()
                .name(shop.getName())
                .description(shop.getDescription())
                .category(categoryDTORequest)
                .discounts(discountDTOList)
                .build();
    }

    @Transactional
    public Shop create(ShopDTO shopDTO) throws ObjectNotFoundException {

        if (shopRepository.existsByName(shopDTO.getName())) {
            throw new IllegalArgumentException("Магазин с таким именем уже существует");
        }

        Category category = categoryRepository.findById(shopDTO.getCategoryId())
                .orElseThrow(() -> new ObjectNotFoundException(shopDTO.getCategoryId(), "Категория"));

        Shop newShop = Shop.builder()
                .name(shopDTO.getName())
                .description(shopDTO.getDescription())
                .category(category)
                .build();

        return shopRepository.save(newShop);
    }

    public Shop update(Long id, ShopDTO shopDTO) throws ObjectNotFoundException {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        if (shopRepository.existsByNameAndIdNot(shopDTO.getName(), id)) {
            throw new IllegalArgumentException("Магазин с таким именем уже существует");
        }

        shop.setName(shopDTO.getName());
        shop.setDescription(shopDTO.getDescription());
        shop.setCategory(categoryRepository.findById(shopDTO.getCategoryId()).orElseThrow(() -> new ObjectNotFoundException(shopDTO.getCategoryId(), "Категория")));

        return shopRepository.save(shop);
    }

    @Transactional(rollbackFor = ObjectNotFoundException.class)
    public String delete(Long id) throws Exception {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));
        try {
            clientRepository.deleteAll(shop.getAdmins());
            discountRepository.deleteAll(shop.getDiscounts());
        } catch (Exception e) {
            throw new Exception("Админы или Предложения не найдены");
        }

        shopRepository.deleteById(id);
        return "deleted";
    }
}
