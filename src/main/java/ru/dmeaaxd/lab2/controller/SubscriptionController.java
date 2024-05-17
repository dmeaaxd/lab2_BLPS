package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.SubscriptionDTO;
import ru.dmeaaxd.lab2.dto.SubscriptionRequestDTO;
import ru.dmeaaxd.lab2.service.SubscriptionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscription")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions() {
        return new ResponseEntity<>(subscriptionService.getSubscriptions(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        Long shopId = subscriptionRequestDTO.getShopId();
        int duration = subscriptionRequestDTO.getDuration();

        Map<String, String> response = new HashMap<>();
        if (subscriptionRequestDTO.antiChecker()) {
            response.put("error", "Переданы неверные параметры");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        SubscriptionDTO subscribe = null;
        try {
            subscribe = subscriptionService.subscribe(shopId, duration);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }


        if (subscribe == null) {
            response.put("error", "У клиента недостаточно средств");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        response.put("message", "Подписка на магазин " + shopId + " для клиента оформлена/продлена на " + duration + " дней");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}