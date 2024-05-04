package ru.dmeaaxd.lab2.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dmeaaxd.lab2.entity.auth.Client;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private int accountBill;

    @OneToOne
    @JoinColumn(name = "clientId")
    private Client client;
}
