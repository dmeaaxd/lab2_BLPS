package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.CategoryDTO;
import ru.dmeaaxd.lab2.dto.DiscountDTO;
import ru.dmeaaxd.lab2.dto.shop.ShopDTO;
import ru.dmeaaxd.lab2.dto.shop.ShopGetAllViewDTO;
import ru.dmeaaxd.lab2.dto.shop.ShopGetCurrentViewDTO;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.entity.Discount;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.CategoryRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    public List<ShopGetAllViewDTO> getAll() {
        List<ShopGetAllViewDTO> shopGetAllViewDTOList = new ArrayList<>();

        for (Shop shop : shopRepository.findAll()){
            List<Category> categories = shop.getCategories();
            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            for (Category category : categories){
                categoryDTOList.add(CategoryDTO.builder()
                        .name(category.getName())
                        .build());
            }

            shopGetAllViewDTOList.add(ShopGetAllViewDTO.builder()
                    .name(shop.getName())
                    .description(shop.getDescription())
                    .categories(categoryDTOList)
                    .build());
        }
        return shopGetAllViewDTOList;
    }

    public ShopGetCurrentViewDTO getCurrent(Long id) throws Exception {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        List<Category> categories = shop.getCategories();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categories){
            categoryDTOList.add(CategoryDTO.builder()
                    .name(category.getName())
                    .build());
        }

        List<Discount> discounts = shop.getDiscounts();
        List<DiscountDTO> discountDTOList = new ArrayList<>();
        for (Discount discount : discounts){
            discountDTOList.add(DiscountDTO.builder()
                    .title(discount.getTitle())
                    .description(discount.getDescription())
                    .promoCode(discount.getPromoCode())
                    .build());
        }

        return ShopGetCurrentViewDTO.builder()
                .name(shop.getName())
                .description(shop.getDescription())
                .categories(categoryDTOList)
                .discounts(discountDTOList)
                .build();
    }

    public Shop create(ShopDTO shopDTO) throws ObjectNotFoundException{
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
