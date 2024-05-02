package ru.dmeaaxd.lab2.dto;


import lombok.Data;

@Data
public class CategoryDTO {
    String name;

    public boolean antiChecker() {
        if (name == null || name.isEmpty()) return true;
        return false;
    }
}
