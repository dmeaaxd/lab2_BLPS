package ru.dmeaaxd.lab1.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SubscriptionDTO {
    private Long clientId;
    private Long shopId;
    private int duration;

    public boolean antiChecker() {
        if (clientId == null) return true;
        if (shopId == null ) return true;
        return false;
    }

}
