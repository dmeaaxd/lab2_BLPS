package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.shop.ShopDTO;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.service.FavoriteService;
import ru.dmeaaxd.lab2.service.ShopService;
import ru.dmeaaxd.lab2.service.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final FavoriteService favoriteService;
    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<Shop>> getAll() {
        return new ResponseEntity<>(shopService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrent(@PathVariable Long id) {

        try {
            return new ResponseEntity<>(shopService.getCurrent(id), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого магазина нет");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ShopDTO shopDTO,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (shopDTO.antiCheckerRegister()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Переданы неверные параметры в запросе");
        }

        return new ResponseEntity<>(shopService.create(shopDTO), HttpStatus.OK);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody ShopDTO shopDTO,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (shopDTO.antiCheckerRegister()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Переданы неверные параметры в запросе");
        }

        try {
            return new ResponseEntity<>(shopService.update(id, shopDTO), HttpStatus.OK);
        } catch (ObjectNotFoundException exception) {
            return new ResponseEntity<>("Магазин не найден", HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            shopService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ObjectNotFoundException exception) {
            return new ResponseEntity<>("Магазин не найден", HttpStatus.OK);
        }
    }


}