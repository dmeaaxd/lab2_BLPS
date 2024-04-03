package ru.dmeaaxd.lab1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab1.dto.FavoriteDTO;
import ru.dmeaaxd.lab1.dto.ShopDTO;
import ru.dmeaaxd.lab1.entity.Favorite;
import ru.dmeaaxd.lab1.entity.Shop;
import ru.dmeaaxd.lab1.service.FavoriteService;
import ru.dmeaaxd.lab1.service.ShopService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;
    private final FavoriteService favoriteService;


    @PostMapping("/create")
    public ResponseEntity<Shop> register(@RequestBody ShopDTO shopDTO) {
        return new ResponseEntity<>(shopService.create(shopDTO), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Shop>> getAll() {
        return new ResponseEntity<>(shopService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getCurrent(@PathVariable Long id) {
        return new ResponseEntity<>(shopService.getCurrent(id), HttpStatus.OK);
    }

    @PostMapping("/favorite")
    public ResponseEntity<Map<String, String>> favoriteShop(@RequestBody FavoriteDTO favoriteDTO) {
        Long clientId = favoriteDTO.getClientId();
        Long shopId = favoriteDTO.getShopId();

        Favorite addedFavorite = favoriteService.addToFavorite(clientId, shopId);

        Map<String, String> response = new HashMap<>();
        if (addedFavorite != null) {
            response.put("message", "Магазин сохранен для клиента " + clientId + ", магазин: " + shopId);
        } else {
            response.put("message", "Магазин уже добавлен в избранное для клиента " + clientId);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}