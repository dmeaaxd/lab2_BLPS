package ru.dmeaaxd.lab2.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDTO {
    Long clientId;
    int accountBill;
}
