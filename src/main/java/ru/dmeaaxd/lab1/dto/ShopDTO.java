package ru.dmeaaxd.lab1.dto;

import lombok.Data;

@Data
public class ShopDTO {
    private String name;
    private String description;
    private int cashbackPercent;

    public boolean antiCheckerRegister() {
        if (name == null || name.isEmpty()) return true;
        if (description == null || description.isEmpty()) return true;
        return false;
    }
}
