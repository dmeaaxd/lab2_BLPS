package ru.dmeaaxd.lab2.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.BillDTO;
import ru.dmeaaxd.lab2.dto.CategoryDTO;
import ru.dmeaaxd.lab2.entity.Bill;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.repository.BillRepository;
import ru.dmeaaxd.lab2.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final ClientRepository clientRepository;


    public int getBill(Long clientId) throws Exception {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Bill bill = client.getAccountBill();
            return bill.getAccountBill();
        } else {
            throw new Exception("Клиент с ID " + clientId + " не найден");
        }
    }

    public int topUp(Long clientId, int amount) throws Exception {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Bill bill = client.getAccountBill();
            int currentBalance = bill.getAccountBill();
            bill.setAccountBill(currentBalance + amount);
            billRepository.save(bill);

            return bill.getAccountBill();

        } else {
            throw new IllegalArgumentException("Клиент с данным ID не найден: " + clientId);
        }
    }

}
