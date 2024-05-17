package ru.dmeaaxd.lab2.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.payment.PaymentDTO;
import ru.dmeaaxd.lab2.service.BillService;
import ru.dmeaaxd.lab2.validators.ValidationResult;

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
    public ResponseEntity<?> topUp(@RequestBody PaymentDTO paymentDTO) {
        Map<String, String> response = new HashMap<>();

        ValidationResult validationResult = paymentDTO.validate();
        if (!validationResult.isCorrect()){
            response.put("error", validationResult.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            response.put("billAmount", String.valueOf(billService.topUp(paymentDTO.getAmount())));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
