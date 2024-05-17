package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.FavoriteDTO;
import ru.dmeaaxd.lab2.dto.FavoritesRequestDTO;
import ru.dmeaaxd.lab2.entity.Favorite;
import ru.dmeaaxd.lab2.service.FavoriteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAll() {
        return new ResponseEntity<>(favoriteService.getFavorites(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/add/{shopId}")
    public ResponseEntity<?> favoriteShop(@PathVariable Long shopId) {

        Favorite favorite = null;
        Map<String, String> response = new HashMap<>();
        try {
            favorite = favoriteService.add(shopId);
        } catch (Exception e) {
            response.put("message", "Магазин " + shopId + " не найден");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        if (favorite != null) {
            response.put("message", "Магазин: " + shopId + " добавлен в избранное для клиента");
        } else {
            response.put("error", "Магазин уже добавлен в избранное для клиента");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();

        try {
            response.put("favoriteId", String.valueOf(favoriteService.remove(id)));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", "Объекта Избранное с данным ID не существует: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}