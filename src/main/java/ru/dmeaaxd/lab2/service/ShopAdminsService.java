package ru.dmeaaxd.lab2.service;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmeaaxd.lab2.dto.client.ClientShopAdminViewDTO;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.entity.auth.Role;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.RoleRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;

import java.util.ArrayList;
import java.util.HashSet;
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

    @Transactional
    public List<ClientShopAdminViewDTO> updateShopAdmins(Long id, List<Long> clients) throws Exception {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Магазин"));

        // Снимаем всех старых администраторов
        Role baseRole = roleRepository.findByName("USER");
        for (Client lastAdmin : shop.getAdmins()){
            lastAdmin.setRoles(new HashSet<>(List.of(new Role[]{baseRole})));
            lastAdmin.setShop(null);
            clientRepository.save(lastAdmin);
        }

        List<Client> admins = new ArrayList<>();

        // Добавляем новых администраторов
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

            admins.add(client);
        }
        shop.setAdmins(admins);

        List<ClientShopAdminViewDTO> shopAdminViewDTOList = new ArrayList<>();
        for (Client client : shop.getAdmins()) {
            shopAdminViewDTOList.add(ClientShopAdminViewDTO.builder()
                    .id(client.getId())
                    .username(client.getUsername())
                    .build());
        }

        return shopAdminViewDTOList;
    }
}
