package ru.dmeaaxd.lab2.utils;

public enum Sort {
    ASC,
    DESC;

    public static Sort parseSort(String source) throws IllegalArgumentException{
        switch (source){
            case ("ASC"), ("asc"):
                return Sort.ASC;
            case ("DESC"), ("desc"):
                return Sort.DESC;
            default:
                throw new IllegalArgumentException("Нет такого sort");
        }
    }
}
