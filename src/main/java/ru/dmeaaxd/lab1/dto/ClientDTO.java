package ru.dmeaaxd.lab1.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private String username;
    private String email;
    private String password;
    private int accountBill;
}
