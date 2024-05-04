package ru.dmeaaxd.lab2.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.service.BillService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private final BillService billService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/bill")
    public ResponseEntity<?> getBill() {
        Map<String, String> response = new HashMap<>();
        try {
            response.put("bill", String.valueOf(billService.getBill()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/topUp")
    public ResponseEntity<?> topUp(@RequestParam int amount) {
        Map<String, String> response = new HashMap<>();
        try {
            response.put("billAmount", String.valueOf(billService.topUp(amount)));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
