package ru.dmeaaxd.lab1.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private String username;
    private String email;
    private String password;
    private int accountBill;

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

