package ru.dmeaaxd.lab2.dto.discounts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    String title;
    String description;
    String promoCode;
}
