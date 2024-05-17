package ru.dmeaaxd.lab2.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientShopAdminViewDTO {
    private Long id;
    private String username;
}
