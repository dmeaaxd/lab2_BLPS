package ru.dmeaaxd.lab2.dto.category;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTOResponse {
    long id;
    String name;
}
