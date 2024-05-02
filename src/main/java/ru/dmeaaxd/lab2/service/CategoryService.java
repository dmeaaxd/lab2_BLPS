package ru.dmeaaxd.lab2.service;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.CategoryDTO;
import ru.dmeaaxd.lab2.dto.ClientDTO;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.entity.Client;
import ru.dmeaaxd.lab2.entity.Favorite;
import ru.dmeaaxd.lab2.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Long create(CategoryDTO categoryDTO) throws Exception {
        Category newCategory = Category.builder()
                .name(categoryDTO.getName())
                .build();

        if (categoryRepository.findByName(newCategory.getName()).isPresent()) {
            throw new Exception("Эта категория уже есть");
        }

        categoryRepository.save(newCategory);

        return newCategory.getId();
    }


    public Long update(Long id, CategoryDTO categoryDTO) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new Exception("Категория с данным ID не найдена: ");
        }

        Category category = optionalCategory.get();
        String newName = categoryDTO.getName();

        if (newName != null && !newName.equals(category.getName())) {
            if (categoryRepository.findByName(newName).isPresent()) {
                throw new Exception("Категория с данным ID уже существует: ");
            }
            category.setName(newName);
        }

        categoryRepository.save(category);

        return category.getId();
    }


    public Long delete(Long id) throws Exception {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty()) {
            throw new Exception("Категория не найдена");
        }

        Category category = optionalCategory.get();
        categoryRepository.delete(category);

        return category.getId();
    }

    public List<CategoryDTO> getAll() {

        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for (Category category : categoryRepository.findAll()) {
            categoryDTOList.add(CategoryDTO.builder()
                            .name(category.getName())
                            .build());
        }
        return categoryDTOList;
    }
}
