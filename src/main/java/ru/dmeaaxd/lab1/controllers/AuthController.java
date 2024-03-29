package ru.dmeaaxd.lab1.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmeaaxd.lab1.entity.controllers.auth.AuthenticationRequest;
import ru.dmeaaxd.lab1.entity.controllers.auth.AuthenticationResponse;
import ru.dmeaaxd.lab1.entity.controllers.auth.ClientRegisterRequest;
import ru.dmeaaxd.lab1.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> clientRegister(
            @RequestBody ClientRegisterRequest request
    ) {
        return ResponseEntity.ok(service.clientRegister(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.auth(request));
    }

}
