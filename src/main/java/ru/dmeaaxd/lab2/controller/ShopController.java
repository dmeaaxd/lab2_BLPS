package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.shop.ShopDTO;
import ru.dmeaaxd.lab2.dto.shop.ShopGetAllViewDTO;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.service.ShopService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;

    // TODO: Добавить фильтрацию, сортивка, пагинация
    @GetMapping
    public ResponseEntity<List<ShopGetAllViewDTO>> getAll() {
        return new ResponseEntity<>(shopService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrent(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();

        try {
            return new ResponseEntity<>(shopService.getCurrent(id), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Такого магазина нет");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ShopDTO shopDTO) {
        Map<String, String> response = new HashMap<>();
        if (shopDTO.antiCheckerRegister()) {
            response.put("error", "Переданы неверные параметры в запросе");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("id", String.valueOf(shopService.create(shopDTO).getId()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ObjectNotFoundException exception) {
            response.put("error", "Такой категории нет (id=" + exception.getIdentifier() + ")");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @PostMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody ShopDTO shopDTO,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {

        Map<String, String> response = new HashMap<>();

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (shopDTO.antiCheckerRegister()) {
            response.put("error", "Переданы неверные параметры в запросе");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("id", String.valueOf(shopService.update(id, shopDTO).getId()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ObjectNotFoundException exception) {
            response.put("error", "Такой категории нет" + exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            shopService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            response.put("error", "Такого магазина нет");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}