package ru.dmeaaxd.lab1.entity.controllers.auth;

public record ClientRegisterRequest(
        String username,
        String email,
        String password,
        Integer account_number
) {}
