package ru.dmeaaxd.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dmeaaxd.lab2.entity.auth.Client;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;

    @OneToMany
    @JoinColumn(name = "discountId")
    private List<Discount> discounts;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @OneToMany
    @JoinColumn(name = "clientId")
    private List<Client> admins; // Администраторы магазина
}
