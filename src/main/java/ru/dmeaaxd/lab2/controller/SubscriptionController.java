package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.FavoriteDTO;
import ru.dmeaaxd.lab2.dto.SubscriptionDTO;
import ru.dmeaaxd.lab2.dto.SubscriptionRequestDTO;
import ru.dmeaaxd.lab2.entity.Subscription;
import ru.dmeaaxd.lab2.service.SubscriptionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscription")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(@RequestHeader(value = "Auth", required = false) Long clientId) {
        return new ResponseEntity<>(subscriptionService.getSubscriptions(clientId), HttpStatus.OK);
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