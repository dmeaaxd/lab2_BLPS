package ru.dmeaaxd.lab1.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab1.dto.ClientDTO;
import ru.dmeaaxd.lab1.entity.Client;
import ru.dmeaaxd.lab1.repository.ClientRepository;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client register(ClientDTO clientDTO) {
        Client newClient = Client.builder()
                .username(clientDTO.getUsername())
                .email(clientDTO.getEmail())
                .password(clientDTO.getPassword())
                .accountBill(clientDTO.getAccountBill())
                .build();

        return clientRepository.save(newClient);
    }

    public Client login(ClientDTO clientDTO) {
        Client client = clientRepository.findByUsername(clientDTO.getUsername());

        if (client == null) {
            throw new RuntimeException("Пользователь с указанным именем не найден");
        }

        if (!client.getPassword().equals(clientDTO.getPassword())) {
            throw new RuntimeException("Неправильный пароль");
        }

        return client;
    }

}
