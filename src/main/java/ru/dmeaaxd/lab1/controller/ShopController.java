package ru.dmeaaxd.lab1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab1.dto.ClientDTO;
import ru.dmeaaxd.lab1.dto.ShopDTO;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.entity.Shop;
import ru.dmeaaxd.lab1.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {

    private final ShopService shopService;


    @PostMapping("/create")
    public ResponseEntity<Shop> register(@RequestBody ShopDTO shopDTO) {
        return new ResponseEntity<>(shopService.create(shopDTO), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Shop>> getAll(@RequestBody ShopDTO shopDTO) {
        return new ResponseEntity<>(shopService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getCurrent(@PathVariable Long id) {
        return new ResponseEntity<>(shopService.getCurrent(id), HttpStatus.OK);
    }

}
