package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmeaaxd.lab2.dto.DiscountDTO;
import ru.dmeaaxd.lab2.entity.auth.Client;
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


    @Transactional
    public DiscountDTO getCurrent(Long shopId, Long discountId) {
        shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));
        Discount discount = discountRepository.findById(discountId).orElseThrow(() -> new ObjectNotFoundException(discountId, "Предложение"));

        return DiscountDTO.builder()
                .title(discount.getTitle())
                .description(discount.getDescription())
                .promoCode(discount.getPromoCode())
                .build();
    }


    @Transactional
    public Discount create(Long shopId, DiscountDTO discountDTO) throws ObjectNotFoundException, IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);

        if (checkClientRights(client.getId(), shopId)){
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

    @Transactional
    public Discount update(Long shopId, Long discountId, DiscountDTO discountDTO) throws IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);

        if (checkClientRights(client.getId(), shopId)) {
            shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));
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
    public void delete(Long shopId, Long discountId) throws ObjectNotFoundException, IllegalAccessException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);

        if (checkClientRights(client.getId(), shopId)) {
            shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));
            discountRepository.findById(discountId).orElseThrow(() -> new ObjectNotFoundException(discountId, "Предложение"));
            discountRepository.deleteById(discountId);
        }
        else {
            throw new IllegalAccessException("У пользователя недостаточно прав");
        }
    }


    public boolean checkClientRights(Long clientId, Long shopId){
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Пользователь"));
        shopRepository.findById(shopId).orElseThrow(() -> new ObjectNotFoundException(shopId, "Магазин"));

        return client.getShop() != null && Objects.equals(client.getShop().getId(), shopId);
    }

}
