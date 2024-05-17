package ru.dmeaaxd.lab2.service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmeaaxd.lab2.entity.Bill;
import ru.dmeaaxd.lab2.entity.auth.Client;
import ru.dmeaaxd.lab2.repository.BillRepository;
import ru.dmeaaxd.lab2.repository.ClientRepository;

@Service
@AllArgsConstructor
public class BillService {

    private final BillRepository billRepository;
    private final ClientRepository clientRepository;

    public int getBill() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);
        Bill bill = client.getAccountBill();
        if (bill == null){
            throw new Exception("Для данного пользователя нет созданного счета");
        }
        return bill.getAccountBill();
    }


    public int topUp(int amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Client client = clientRepository.findByUsername(username);
        Bill bill = client.getAccountBill();
        // Если счет не существует, создаем новую запись
        if (bill == null) {
            bill = new Bill();
            client.setAccountBill(bill);
            bill.setAccountBill(amount);
        } else {
            int currentBalance = bill.getAccountBill();
            bill.setAccountBill(currentBalance + amount);
        }
        billRepository.save(bill);
        return bill.getAccountBill();
    }


}
