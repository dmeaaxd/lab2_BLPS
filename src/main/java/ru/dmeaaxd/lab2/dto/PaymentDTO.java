package ru.dmeaaxd.lab2.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dmeaaxd.lab2.validators.ValidationResult;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    String cardNumber;
    int amount;

    public ValidationResult validate() {
        if (cardNumber == null || cardNumber.length() != 16){
            return new ValidationResult(false, "Неверно задан cardNumber");
        }

        boolean flag = false;
        for (int i = 0; i < cardNumber.length(); i++){
            try {
                int tmp = Integer.parseInt(String.valueOf(cardNumber.charAt(i)));
            } catch (NumberFormatException numberFormatException){
                flag = true;
            }
        }
        if (flag) return new ValidationResult(false, "cardNumber должен состоять из 16 цифр");

        if (amount <= 0) return new ValidationResult(false, "amount не может быть меньше или равным 0");

        return new ValidationResult(true, null);
    }
}
