package ru.dmeaaxd.lab2.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShopDTO {
    private String name;
    private String description;
    private List<Long> categories;

    public boolean antiCheckerRegister() {
        if (name == null || name.isEmpty()) return true;
        if (description == null || description.isEmpty()) return true;
        return false;
    }
}
