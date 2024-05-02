package ru.dmeaaxd.lab2.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class SubscriptionDTO {
    private Long clientId;
    private Long shopId;
    private int duration;
}
