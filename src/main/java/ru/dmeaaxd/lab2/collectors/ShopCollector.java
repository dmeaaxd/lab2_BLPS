package ru.dmeaaxd.lab2.collectors;

import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import ru.dmeaaxd.lab2.dto.shop.ShopFilters;
import ru.dmeaaxd.lab2.repository.CategoryRepository;
import ru.dmeaaxd.lab2.repository.ShopRepository;
import ru.dmeaaxd.lab2.utils.Sort;
import ru.dmeaaxd.lab2.validators.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShopCollector {
    private final ShopRepository shopRepository;
    private final CategoryRepository categoryRepository;

    public ShopFilters collectFilters(Map<String, String> allParams){
        List<Integer> categories = new ArrayList<>();
        Sort sort = null;
        int page = -1;

        for (String param : allParams.keySet()){
            switch (param){
                case ("categories"):
                    categories.addAll(Arrays.stream(allParams.get(param).split(","))
                            .map(Integer::parseInt)
                            .toList());
                    break;

                case ("sort"):
                    sort = Sort.parseSort(allParams.get(param));
                    break;

                case ("page"):
                    page = Integer.parseInt(allParams.get(param));
                    break;
            }
        }

        return ShopFilters.builder()
                .categories(categories)
                .page(page)
                .sort(sort)
                .build();
    }
}
