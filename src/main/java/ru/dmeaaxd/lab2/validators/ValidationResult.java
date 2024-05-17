package ru.dmeaaxd.lab2.validators;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ValidationResult {
    private boolean correct;
    private String message;
}
