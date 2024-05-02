package ru.dmeaaxd.lab2.dto.shop;

import ru.dmeaaxd.lab2.dto.CategoryDTO;
import ru.dmeaaxd.lab2.dto.DiscountDTO;
import ru.dmeaaxd.lab2.entity.Discount;

import java.util.List;

public class ShopGetCurrentViewDTO {
    private String name;
    private String description;
    private List<CategoryDTO> categories;
    private List<DiscountDTO> discounts;
}
