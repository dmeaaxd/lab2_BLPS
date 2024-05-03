package ru.dmeaaxd.lab2.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmeaaxd.lab2.entity.Bill;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.repository.BillRepository;
import ru.dmeaaxd.lab2.repository.ClientRepository;

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
            throw new Exception("Счёт клиента с данным ID не найден: " + clientId);
        }
    }


    @Transactional
    public int topUp(Long clientId, int amount) throws Exception {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            Bill bill = client.getAccountBill();

            // Если счет не существует, создаем новую запись
            if (bill == null) {
                bill = new Bill();
                bill.setClient(clientRepository.findById(clientId).get());
                bill.setAccountBill(amount);
            } else {
                int currentBalance = bill.getAccountBill();
                bill.setAccountBill(currentBalance + amount);
            }

            billRepository.save(bill);

            return bill.getAccountBill();
        } else {
            throw new IllegalArgumentException("Клиент с данным ID не найден: " + clientId);
        }
    }


}
