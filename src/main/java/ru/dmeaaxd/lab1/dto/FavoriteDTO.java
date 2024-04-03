package ru.dmeaaxd.lab1.dto;

import lombok.Data;
import lombok.Getter;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.entity.Shop;

@Data
@Getter
public class FavoriteDTO {
    private Long clientId;
    private Long shopId;
}
