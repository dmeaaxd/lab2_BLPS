package ru.dmeaaxd.lab2.dto.shop;

import lombok.Builder;
import lombok.Data;
import ru.dmeaaxd.lab2.dto.category.CategoryDTORequest;
import ru.dmeaaxd.lab2.dto.DiscountDTO;

import java.util.List;

@Data
@Builder
public class ShopGetCurrentViewDTO {
    private String name;
    private String description;
    private CategoryDTORequest category;
    private List<DiscountDTO> discounts;
}
