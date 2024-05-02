package ru.dmeaaxd.lab2.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    String name;

    public boolean antiChecker() {
        if (name == null || name.isEmpty()) return true;
        return false;
    }
}
