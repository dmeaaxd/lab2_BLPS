package ru.dmeaaxd.lab2.dto.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {
    private String username;
    private String email;
    private String password;

    public boolean antiCheckerRegister() {
        if (username == null || username.isEmpty()) return true;
        if (email == null || email.isEmpty()) return true;
        if (password == null || password.isEmpty()) return true;
        return false;
    }

    public boolean antiCheckerLogin() {
        if (username == null || username.isEmpty()) return true;
        if (password == null || password.isEmpty()) return true;
        return false;
    }
}

