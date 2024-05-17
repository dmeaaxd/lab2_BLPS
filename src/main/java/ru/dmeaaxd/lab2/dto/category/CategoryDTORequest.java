package ru.dmeaaxd.lab2.dto.category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTORequest {
    String name;
    String description;

    public boolean antiChecker() {
        if (name == null || name.isEmpty()) return true;
        return false;
    }
}