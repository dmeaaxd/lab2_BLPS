package ru.dmeaaxd.lab2.dto.shop;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDTO {
    private String name;
    private String description;
    private Long categoryId;

    public boolean antiCheckerRegister() {
        if (name == null || name.isEmpty()) return true;
        if (description == null || description.isEmpty()) return true;
        return false;
    }
}
