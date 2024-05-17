package ru.dmeaaxd.lab2.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDTO {
    private Long clientId;
    private Long shopId;
}
