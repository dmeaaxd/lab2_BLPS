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
    public ResponseEntity<Client> register(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.register(clientDTO), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<Client> login(@RequestBody ClientDTO clientDTO) {
        return new ResponseEntity<>(clientService.login(clientDTO), HttpStatus.OK);
    }

}
