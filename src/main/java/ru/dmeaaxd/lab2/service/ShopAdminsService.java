package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.client.ClientShopAdminViewDTO;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.entity.auth.Role;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.RoleRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ShopAdminsService {

    ShopRepository shopRepository;
    ClientRepository clientRepository;
    RoleRepository roleRepository;

    public List<ClientShopAdminViewDTO> getShopAdmins(Long id) throws ObjectNotFoundException {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        List<ClientShopAdminViewDTO> shopAdminViewDTOList = new ArrayList<>();
        for (Client client : shop.getAdmins()) {
            shopAdminViewDTOList.add(ClientShopAdminViewDTO.builder()
                    .id(client.getId())
                    .username(client.getUsername())
                    .build());
        }

        return shopAdminViewDTOList;
    }

    public List<ClientShopAdminViewDTO> updateShopAdmins(Long id, List<Long> clients) throws Exception {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        for (Long clientID : clients) {
            Client client = clientRepository.findById(clientID).orElseThrow(() -> new ObjectNotFoundException(id, "Пользователь"));
            Set<Role> roles = client.getRoles();
            Role shop_admin = roleRepository.findByName("SHOP_ADMIN");

            if (roles.contains(shop_admin)) {
                throw new Exception("Пользователь " + clientID + "уже является администратором другого магазина");
            }

            roles.add(shop_admin);
            client.setRoles(roles);
            client.setShop(shop);
            clientRepository.save(client);
        }

        return getShopAdmins(id);
    }
}
