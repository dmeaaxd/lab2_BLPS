package ru.dmeaaxd.lab2.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class FavoritesRequestDTO {
    private Long shopId;

    public boolean antiChecker() {
        if (shopId == null ) return true;
        return false;
    }
}
