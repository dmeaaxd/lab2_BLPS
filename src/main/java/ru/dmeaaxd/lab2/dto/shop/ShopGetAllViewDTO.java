package ru.dmeaaxd.lab2.dto.shop;

import lombok.Builder;
import lombok.Data;
import ru.dmeaaxd.lab2.dto.category.CategoryDTORequest;

@Builder
@Data
public class ShopGetAllViewDTO {
    private Long id;
    private String name;
    private String description;
    private CategoryDTORequest category;
}
