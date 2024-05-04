package ru.dmeaaxd.lab2.service;


import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.client.ClientDTO;
import ru.dmeaaxd.lab2.dto.client.RegisterDTO;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.entity.auth.Role;
import ru.dmeaaxd.lab2.repository.ClientRepository;
import ru.dmeaaxd.lab2.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientRepository clientRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public ClientDTO register(RegisterDTO registerDTO) throws Exception {
        if (clientRepository.existsByUsername(registerDTO.getUsername())) {
            throw new Exception("Username is already taken!");
        }

        if (registerDTO.antiCheckerRegister()){
            throw new IllegalArgumentException("Данные введены некорректно, все поля должны быть заполнены");
        }

        if (clientRepository.existsByEmail(registerDTO.getEmail())) {
            throw new Exception("Email is already taken!");
        }

        Client client = Client.builder()
                .username(registerDTO.getUsername())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword())).build();

        Role role = roleRepository.findByName("USER");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        client.setRoles(roles);

        Client resultClient = clientRepository.save(client);
        return ClientDTO.builder()
                .username(resultClient.getUsername())
                .email(resultClient.getUsername())
                .roles(resultClient.getRoles())
                .build();
    }
}
