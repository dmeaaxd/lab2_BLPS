package ru.dmeaaxd.lab2.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private Long clientId;
    private Long shopId;
    private int duration;
}
