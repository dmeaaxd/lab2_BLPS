package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectDeletedException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.client.ClientDTO;
import ru.dmeaaxd.lab2.dto.client.ClientShopAdminViewDTO;
import ru.dmeaaxd.lab2.service.ShopAdminsService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/shop_admins")
@AllArgsConstructor
public class ShopAdminsController {
    private final ShopAdminsService shopAdminsService;

    @GetMapping("/{id}/")
    public ResponseEntity<?> getShopAdmins(@PathVariable Long id,
                                           @RequestHeader(value = "Auth", required = false) Long clientId) {
        Map<String, String> response = new HashMap<>();

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            return new ResponseEntity<>(shopAdminsService.getShopAdmins(id), HttpStatus.OK);
        } catch (ObjectNotFoundException exception){
            response.put("error", "Такого магазина нет");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestParam List<Long> clients,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {
        Map<String, String> response = new HashMap<>();

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            return new ResponseEntity<>(shopAdminsService.updateShopAdmins(id, clients), HttpStatus.OK);
        } catch (ObjectNotFoundException exception){
            if (Objects.equals(exception.getEntityName(), "Магазин")){
                response.put("error", "Такого магазина нет");
            }
            else {
                response.put("error", "Такого пользователя нет (id=" + exception.getIdentifier() + ")");
            }
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
