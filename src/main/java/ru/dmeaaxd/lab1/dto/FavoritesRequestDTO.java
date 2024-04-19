package ru.dmeaaxd.lab1.dto;

import lombok.Data;
import lombok.Getter;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.entity.Shop;

@Data
@Getter
public class FavoritesRequestDTO {
    private Long shopId;

    public boolean antiChecker() {
        if (shopId == null ) return true;
        return false;
    }
}
