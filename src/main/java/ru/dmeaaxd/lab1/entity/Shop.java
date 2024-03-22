package ru.dmeaaxd.lab1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Shops")
@Getter
@Setter
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;

    private String shopName;
    private String description;
    private double cashbackPercent;

    // Конструкторы
    public Shop() {}

    public Shop(String shopName, String description, double cashbackPercent) {
        this.shopName = shopName;
        this.description = description;
        this.cashbackPercent = cashbackPercent;
    }
}
