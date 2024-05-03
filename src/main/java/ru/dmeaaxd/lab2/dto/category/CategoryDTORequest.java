package ru.dmeaaxd.lab2.dto.category;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTORequest {
    String name;
    String description;

    public boolean antiChecker() {
        if (name == null || name.isEmpty()) return true;
        return false;
    }
}