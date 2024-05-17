package ru.dmeaaxd.lab2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritesRequestDTO {
    private Long shopId;

    public boolean antiChecker() {
        if (shopId == null ) return true;
        return false;
    }
}
