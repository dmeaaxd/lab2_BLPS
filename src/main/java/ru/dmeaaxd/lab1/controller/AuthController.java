package ru.dmeaaxd.lab1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmeaaxd.lab1.dto.ClientDTO;
import ru.dmeaaxd.lab1.service.ClientService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ClientDTO clientDTO) {

        Map<String, String> response = new HashMap<>();

        if (clientDTO.antiCheckerRegister()) {
            response.put("error", "Переданы неверные параметры в запросе");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseEntity<>(clientService.register(clientDTO), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Пользователь с данным username уже есть: " + clientDTO.getUsername());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientDTO clientDTO) {

        Map<String, String> response = new HashMap<>();

        if (clientDTO.antiCheckerLogin()) {
            response.put("error", "Переданы неверные параметры в запросе");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

        }

        try {
            return new ResponseEntity<>(clientService.login(clientDTO), HttpStatus.OK);
        } catch (Exception e) {
            response.put("error", "Неверный логин или пароль");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}
