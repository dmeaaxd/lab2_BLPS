package ru.dmeaaxd.lab2.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.ClientDTO;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.repository.ClientRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Long register(ClientDTO clientDTO) throws Exception {
        Client newClient = Client.builder()
                .username(clientDTO.getUsername())
                .email(clientDTO.getEmail())
                .password(clientDTO.getPassword())
                .accountBill(clientDTO.getAccountBill())
                .build();

        if (clientRepository.findByUsername(newClient.getUsername()).isPresent()) {
            throw new Exception("Это имя пользователя уже занято");
        }

        clientRepository.save(newClient);

        return newClient.getId();
    }


    public Long login(ClientDTO clientDTO) throws Exception {
        Optional<Client> oldClient = clientRepository.findByUsername(clientDTO.getUsername());

        if (!oldClient.isPresent()) {
            throw new Exception("Пользователь с указанным именем не найден");
        }

        Client client = oldClient.get();

        if (!client.getPassword().equals(clientDTO.getPassword())) {
            throw new Exception("Неправильный пароль");
        }

        return client.getId();
    }

}
