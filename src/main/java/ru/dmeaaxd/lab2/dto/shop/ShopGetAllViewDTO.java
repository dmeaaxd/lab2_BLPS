package ru.dmeaaxd.lab2.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dmeaaxd.lab2.dto.category.CategoryDTORequest;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopGetAllViewDTO {
    private Long id;
    private String name;
    private String description;
    private CategoryDTORequest category;
}
