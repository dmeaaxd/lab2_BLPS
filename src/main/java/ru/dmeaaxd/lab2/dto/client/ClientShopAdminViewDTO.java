package ru.dmeaaxd.lab2.dto.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientShopAdminViewDTO {
    private Long id;
    private String username;
}
