package ru.dmeaaxd.lab2.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class SubscriptionRequestDTO {
    private Long shopId;
    private int duration;

    public boolean antiChecker() {
        if (shopId == null ) return true;
        return false;
    }

}
