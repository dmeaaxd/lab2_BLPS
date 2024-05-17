package ru.dmeaaxd.lab2.utils;
import ru.dmeaaxd.lab2.entity.Shop;
import java.util.Comparator;

public class ShopComparator implements Comparator<Shop> {

    @Override
    public int compare(Shop o1, Shop o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
