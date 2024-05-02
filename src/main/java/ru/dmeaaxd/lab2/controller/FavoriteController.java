package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.FavoritesRequestDTO;
import ru.dmeaaxd.lab2.entity.Favorite;
import ru.dmeaaxd.lab2.service.FavoriteService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;


    @PostMapping("/add")
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
}