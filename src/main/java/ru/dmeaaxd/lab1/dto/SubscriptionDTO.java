package ru.dmeaaxd.lab1.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SubscriptionDTO {
    private Long clientId;
    private Long shopId;
    private int duration;
}
