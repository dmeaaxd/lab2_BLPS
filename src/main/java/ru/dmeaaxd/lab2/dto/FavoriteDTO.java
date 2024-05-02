package ru.dmeaaxd.lab2.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class FavoriteDTO {
    private Long clientId;
    private Long shopId;
}
