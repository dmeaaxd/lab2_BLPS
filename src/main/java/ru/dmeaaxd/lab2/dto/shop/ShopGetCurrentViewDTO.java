package ru.dmeaaxd.lab2.dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dmeaaxd.lab2.dto.category.CategoryDTORequest;
import ru.dmeaaxd.lab2.dto.discounts.DiscountDTO;
import ru.dmeaaxd.lab2.dto.discounts.DiscountInListDTO;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopGetCurrentViewDTO {
    private String name;
    private String description;
    private CategoryDTORequest category;
    private List<DiscountInListDTO> discounts;
}
