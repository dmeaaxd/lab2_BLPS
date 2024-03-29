package ru.dmeaaxd.lab1.entity.controllers.auth;

public record AuthenticationRequest(
        String email,
        String password
){}
