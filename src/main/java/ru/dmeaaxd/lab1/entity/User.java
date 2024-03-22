package ru.dmeaaxd.lab1.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String email;
    private String password;
    private String accountNumber;


    // Конструкторы
    public User() {}

    public User(String username, String email, String password, String accountNumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.accountNumber = accountNumber;
    }
}
