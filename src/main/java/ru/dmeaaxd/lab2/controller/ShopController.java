package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.FavoritesRequestDTO;
import ru.dmeaaxd.lab2.dto.ShopDTO;
import ru.dmeaaxd.lab2.dto.SubscriptionDTO;
import ru.dmeaaxd.lab2.dto.SubscriptionRequestDTO;
import ru.dmeaaxd.lab2.entity.Favorite;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.service.FavoriteService;
import ru.dmeaaxd.lab2.service.ShopService;
import ru.dmeaaxd.lab2.service.SubscriptionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final FavoriteService favoriteService;
    private final SubscriptionService subscriptionService;


    @PostMapping("/create")
    public ResponseEntity<?> register(@RequestBody ShopDTO shopDTO,
                                      @RequestHeader(value = "Auth", required = false) Long clientId) {

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (shopDTO.antiCheckerRegister()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Переданы неверные параметры в запросе");
        }

        return new ResponseEntity<>(shopService.create(shopDTO), HttpStatus.OK);
    }


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

    @PostMapping("/favorite")
    public ResponseEntity<?> favoriteShop(@RequestBody FavoritesRequestDTO favoritesRequestDTO,
                                          @RequestHeader(value = "Auth", required = false) Long clientId) {

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long shopId = favoritesRequestDTO.getShopId();

        if (favoritesRequestDTO.antiChecker()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Переданы неверные параметры в запросе");
        }

        Favorite favorite;
        try {
            favorite = favoriteService.addToFavorite(clientId, shopId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Клиент или магазин не найден");
        }

        Map<String, String> response = new HashMap<>();
        if (favorite != null) {
            response.put("message", "Магазин: " + shopId + " добавлен в избранное для клиента: " + clientId);
        } else {
            response.put("error", "Магазин уже добавлен в избранное для клиента: " + clientId);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO,
                                       @RequestHeader(value = "Auth", required = false) Long clientId) {

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long shopId = subscriptionRequestDTO.getShopId();
        int duration = subscriptionRequestDTO.getDuration();

        if (subscriptionRequestDTO.antiChecker()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Переданы неверные параметры в запросе");
        }

        SubscriptionDTO subscribe = null;
        try {
            subscribe = subscriptionService.subscribe(clientId, shopId, duration);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Клиент или магазин не найден");
        }

        Map<String, String> response = new HashMap<>();
        if (subscribe != null) {
            response.put("message", "Подписка на магазин: " + shopId + " для клиента " + clientId + " оформлена/продлена на " + duration);
        } else {
            response.put("error", "У клиента: " + clientId + " недостаточно средств");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}