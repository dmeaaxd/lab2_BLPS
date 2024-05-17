package ru.dmeaaxd.lab2.dto.shop;

import lombok.Builder;
import lombok.Data;
import ru.dmeaaxd.lab2.utils.Sort;

import java.util.List;

@Data
@Builder
public class ShopFilters {
    private List<Integer> categories;
    private Sort sort;
    private int page;
}
