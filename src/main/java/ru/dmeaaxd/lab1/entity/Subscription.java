package ru.dmeaaxd.lab1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Shops")
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @ManyToOne
    @JoinColumn(name = "user_id_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id_id")
    private Shop shop;

    // Конструкторы
    public Subscription() {
    }

    public Subscription(User user, Shop shop) {
        this.user = user;
        this.shop = shop;
    }

}
