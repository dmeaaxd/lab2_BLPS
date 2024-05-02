package ru.dmeaaxd.lab2.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class FavoriteDTO {
    private Long clientId;
    private Long shopId;
}
