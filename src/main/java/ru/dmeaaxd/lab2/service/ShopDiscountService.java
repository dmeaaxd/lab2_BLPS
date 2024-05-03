package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmeaaxd.lab2.dto.DiscountDTO;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.entity.Discount;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.DiscountRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.Objects;

@Service
@AllArgsConstructor
public class ShopDiscountService {
    private final ShopRepository shopRepository;
    private final DiscountRepository discountRepository;
    private final ClientRepository clientRepository;

    public DiscountDTO getCurrent(Long shopId, Long discountId) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));
        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> new ObjectNotFoundException(discountId, "Предложение"));

        return DiscountDTO.builder()
                .title(discount.getTitle())
                .description(discount.getDescription())
                .promoCode(discount.getPromoCode())
                .build();
    }

    // TODO: Убрать бесконечное дерево
    @Transactional
    public Discount create(Long clientId, Long shopId, DiscountDTO discountDTO) throws IllegalAccessException {
        if (checkClientRights(clientId, shopId)){
            Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));
            Discount discount = Discount.builder()
                    .title(discountDTO.getTitle())
                    .description(discountDTO.getDescription())
                    .promoCode(discountDTO.getPromoCode())
                    .shop(shop).build();
            return discountRepository.save(discount);
        }
        else{
            throw new IllegalAccessException("У пользователя недостаточно прав");
        }
    }

    // TODO: Убрать бесконечное дерево
    @Transactional
    public Discount update(Long clientId, Long shopId, Long discountId, DiscountDTO discountDTO) throws IllegalAccessException {
        if (checkClientRights(clientId, shopId)) {
            Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));
            Discount discount = discountRepository.findById(discountId).orElseThrow(() -> new ObjectNotFoundException(discountId, "Предложение"));

            discount.setTitle(discountDTO.getTitle());
            discount.setDescription(discountDTO.getDescription());
            discount.setPromoCode(discountDTO.getPromoCode());

            return discountRepository.save(discount);
        }
        else{
            throw new IllegalAccessException("У пользователя недостаточно прав");
        }
    }

    @Transactional
    public void delete(Long clientId, Long shopId, Long discountId) throws ObjectNotFoundException, IllegalAccessException {
        if (checkClientRights(clientId, shopId)) {
            Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));
            discountRepository.findById(discountId).orElseThrow(() -> new ObjectNotFoundException(discountId, "Предложение"));
            discountRepository.deleteById(discountId);
        }
        else {
            throw new IllegalAccessException("У пользователя недостаточно прав");
        }
    }


    public boolean checkClientRights(Long clientId, Long shopId){
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Пользователь"));
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));

        return client.getShop() != null && Objects.equals(client.getShop().getId(), shopId);
    }

}
