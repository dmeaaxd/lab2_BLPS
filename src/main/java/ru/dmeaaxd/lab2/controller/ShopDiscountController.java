package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.DiscountDTO;
import ru.dmeaaxd.lab2.service.ShopAdminsService;
import ru.dmeaaxd.lab2.service.ShopDiscountService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/shop_discounts")
@AllArgsConstructor
public class ShopDiscountController {
    private final ShopDiscountService shopDiscountService;

    @GetMapping("/{shopId}/{discountId}")
    public ResponseEntity<?> getCurrent(@PathVariable Long shopId,
                                        @PathVariable Long discountId,
                                        @RequestHeader(value = "Auth", required = false) Long clientId) {
        Map<String, String> response = new HashMap<>();

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            return new ResponseEntity<>(shopDiscountService.getCurrent(shopId, discountId), HttpStatus.OK);
        } catch (ObjectNotFoundException exception){
            if (Objects.equals(exception.getEntityName(), "Магазин")){
                response.put("error", "Такого магазина нет");
            }
            else{
                response.put("error", "Такого предложения нет");
            }
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{shopId}/create")
    public ResponseEntity<?> create(@PathVariable Long shopId,
                                    @RequestBody DiscountDTO discountDTO,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {
        Map<String, String> response = new HashMap<>();

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            return new ResponseEntity<>(shopDiscountService.create(clientId, shopId, discountDTO), HttpStatus.OK);
        } catch (ObjectNotFoundException exception){
            if (Objects.equals(exception.getEntityName(), "Магазин")){
                response.put("error", "Такого магазина нет");
            }
            else{
                response.put("error", "Такого предложения нет");
            }
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException illegalAccessException){
            response.put("error", illegalAccessException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{shopId}/{discountId}/update")
    public ResponseEntity<?> update(@PathVariable Long shopId,
                                    @PathVariable Long discountId,
                                    @RequestBody DiscountDTO discountDTO,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {
        Map<String, String> response = new HashMap<>();

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            return new ResponseEntity<>(shopDiscountService.update(clientId, shopId, discountId, discountDTO), HttpStatus.OK);
        } catch (ObjectNotFoundException exception){
            if (Objects.equals(exception.getEntityName(), "Магазин")){
                response.put("error", "Такого магазина нет");
            }
            else{
                response.put("error", "Такого предложения нет");
            }
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException illegalAccessException){
            response.put("error", illegalAccessException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{shopId}/{discountId}/delete")
    public ResponseEntity<?> delete(@PathVariable Long shopId,
                                    @PathVariable Long discountId,
                                    @RequestHeader(value = "Auth", required = false) Long clientId) {
        Map<String, String> response = new HashMap<>();

        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            shopDiscountService.delete(clientId, shopId, discountId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ObjectNotFoundException exception) {
            if (Objects.equals(exception.getEntityName(), "Магазин")){
                response.put("error", "Такого магазина нет");
            }
            else{
                response.put("error", "Такого предложения нет");
            }
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException illegalAccessException){
            response.put("error", illegalAccessException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
