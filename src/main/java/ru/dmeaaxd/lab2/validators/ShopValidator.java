package ru.dmeaaxd.lab2.validators;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.entity.Category;
import ru.dmeaaxd.lab2.entity.Shop;
import ru.dmeaaxd.lab2.repository.CategoryRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;
import ru.dmeaaxd.lab2.utils.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShopValidator {
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    public ValidationResult getAllRequestIsCorrect(Map<String, String> allParams){
        boolean result = true;
        String message = null;

        List<Integer> categories = new ArrayList<>();
        Sort sort = null;
        int page = -1;

        for (String param : allParams.keySet()){
            if (!result){
                break;
            }

            switch (param){
                case ("categories"):
                    try{
                        categories.addAll(Arrays.stream(allParams.get(param).split(","))
                                .map(Integer::parseInt)
                                .toList());
                        for (int categoryId : categories){
                            categoryRepository.findById((long) categoryId).orElseThrow(() -> new ObjectNotFoundException(categoryId, "Категория"));
                        }
                    } catch (NumberFormatException numberFormatException){
                        result = false;
                        message = "Параметр categories задан неверно";
                    } catch (ObjectNotFoundException objectNotFoundException){
                        result = false;
                        message = "Категория с идентификатором " + objectNotFoundException.getIdentifier() + " не найдена";
                    }
                    break;

                case ("sort"):
                    if (sort != null){
                        result = false;
                        message = "Повторное задание параметра sort";
                        break;
                    }

                    try{
                        sort = Sort.parseSort(allParams.get(param));
                    } catch (IllegalArgumentException illegalArgumentException){
                        result = false;
                        message = "Параметр sort задан неверно";
                    }
                    break;

                case ("page"):
                    if (page != -1){
                        result = false;
                        message = "Повторное задание параметра page";
                        break;
                    }

                    try{
                        page = Integer.parseInt(allParams.get(param));
                    } catch (NumberFormatException numberFormatException){
                        result = false;
                        message = "Параметр page задан неверно";
                    }

//                    // Обработка фильтра categories
//                    List<Category> allowCategories = new ArrayList<>();
//                    if (!filters.getCategories().isEmpty()) {
//                        for (int categoryId : filters.getCategories()) {
//                            allowCategories.add(categoryRepository.findById((long) categoryId).orElseThrow(() -> new ObjectNotFoundException(categoryId, "Категория")));
//                        }
//                    }

//                    // Фильтрация магазинов
//                    List<Shop> filteredShops = new ArrayList<>();
//                    for (int i = min_index; i < max_index; i++){
//                        Shop shop = shops.get(i);
//                        if (!allowCategories.isEmpty() && !allowCategories.contains(shop.getCategory())){
//                            continue;
//                        }
//                        filteredShops.add(shop);
//                    }

                    double shopCount = shopRepository.findAll().size();
                    int pagesCount = (int) Math.ceil(shopCount / shopRepository.PAGE_SIZE);
                    if (pagesCount == 0) pagesCount = 1;

                    if ((page > pagesCount) || page < 1){
                        result = false;
                        message = "Такой страници нет";
                    }
                    break;

                default:
                    result = false;
                    message = "Параметра " + param + " не существует";
            }
        }
        return new ValidationResult(result, message);
    }
}
