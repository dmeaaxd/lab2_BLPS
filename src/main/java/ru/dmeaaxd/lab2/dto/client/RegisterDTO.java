package ru.dmeaaxd.lab2.dto.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
}
