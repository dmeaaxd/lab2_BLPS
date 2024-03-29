package ru.dmeaaxd.lab1.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Favorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoritesId;

    @ManyToOne
    @JoinColumn(name = "user_id_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shop_id_id")
    private Shop shop;

    // Конструкторы
    public Favorites() {
    }

    public Favorites(User user, Shop shop) {
        this.user = user;
        this.shop = shop;
    }
}
