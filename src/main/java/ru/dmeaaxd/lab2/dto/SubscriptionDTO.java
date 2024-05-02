package ru.dmeaaxd.lab2.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SubscriptionDTO {
    private Long clientId;
    private Long shopId;
    private int duration;
}
