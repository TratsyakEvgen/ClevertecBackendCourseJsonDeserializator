package ru.clevertec.json;

public interface Mapper {
    <T> T read(String json, Class<T> targetClass);
}
