package ru.dmeaaxd.lab2.dto.client;

import lombok.Builder;
import lombok.Data;
import ru.dmeaaxd.lab2.entity.auth.Role;

import java.util.Set;

@Data
@Builder
public class ClientDTO {
    private String username;
    private String email;
    private Set<Role> roles;
}

