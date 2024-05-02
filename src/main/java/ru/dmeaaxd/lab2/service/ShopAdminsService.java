package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.client.ClientShopAdminViewDTO;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShopAdminsService {

    ShopRepository shopRepository;
    ClientRepository clientRepository;
    public List<ClientShopAdminViewDTO> getShopAdmins(Long id) throws ObjectNotFoundException{
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        List<ClientShopAdminViewDTO> shopAdminViewDTOList = new ArrayList<>();
        for (Client client : shop.getAdmins()){
            shopAdminViewDTOList.add(ClientShopAdminViewDTO.builder()
                    .id(client.getId())
                    .username(client.getUsername())
                    .build());
        }

        return shopAdminViewDTOList;
    }

    public List<ClientShopAdminViewDTO> updateShopAdmins(Long id, List<Long> clients) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        for (Long clientID : clients){
            Client client = clientRepository.findById(clientID).orElseThrow(() -> new ObjectNotFoundException(id, "Пользователь"));
            client.setShop(shop);
            clientRepository.save(client);
        }

        return getShopAdmins(id);
    }
}
