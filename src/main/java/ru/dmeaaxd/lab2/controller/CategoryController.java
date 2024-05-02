package ru.dmeaaxd.lab2.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dmeaaxd.lab2.dto.CategoryDTO;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.service.CategoryService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO categoryDTO,
                                    @RequestHeader(value = "Auth", required = false) Long clientId)  {
        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, String> response = new HashMap<>();

        if (categoryDTO.antiChecker()) {
            response.put("error", "Переданы неверные параметры в запросе");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("categoryId", String.valueOf(categoryService.create(categoryDTO)));
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("error", "Категория с данным названием уже есть: " + categoryDTO.getName());
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody CategoryDTO categoryDTO,
                                    @RequestHeader(value = "Auth", required = false) Long clientId)  {
        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, String> response = new HashMap<>();

        if (categoryDTO.antiChecker()) {
            response.put("error", "Переданы неверные параметры в запросе");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            response.put("categoryId", String.valueOf(categoryService.update(id, categoryDTO)));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", e + categoryDTO.getName());
            return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestHeader(value = "Auth", required = false) Long clientId)  {
        if (clientId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Map<String, String> response = new HashMap<>();

        try {
            response.put("categoryId", String.valueOf(categoryService.delete(id)));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("error", "Категория с данным ID не существует: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
