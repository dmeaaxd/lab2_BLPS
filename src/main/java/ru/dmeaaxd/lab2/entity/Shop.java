package ru.dmeaaxd.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany
    @JoinColumn(name = "categoryId")
    private List<Category> categories;
}
