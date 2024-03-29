package ru.dmeaaxd.lab1.entity.controllers.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(
        @JsonProperty("user_id") Integer userId
) {}
