package ru.dmeaaxd.lab2.dto.shop;

import lombok.Builder;
import lombok.Data;
import ru.dmeaaxd.lab2.dto.CategoryDTO;

import java.util.List;

@Builder
@Data
public class ShopGetAllViewDTO {
    private String name;
    private String description;
    private CategoryDTO category;
}
