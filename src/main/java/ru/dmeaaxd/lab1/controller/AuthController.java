package ru.dmeaaxd.lab1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmeaaxd.lab1.dto.ClientDTO;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.service.ClientService;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody ClientDTO clientDTO) {

        if (clientDTO.antiCheckerRegister()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Переданы неверные параметры в запросе");
        }

        try {
            return new ResponseEntity<>(clientService.register(clientDTO), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Пользователь с данным username уже есть: " + clientDTO.getUsername());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClientDTO clientDTO) {

        if (clientDTO.antiCheckerLogin()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Переданы неверные параметры в запросе");
        }

        try {
            return new ResponseEntity<>(clientService.login(clientDTO), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный логин или пароль");
        }
    }

}
