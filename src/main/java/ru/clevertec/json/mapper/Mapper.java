package ru.clevertec.json.mapper;

public interface Mapper {
    <T> T read(String json, Class<T> targetClass);
}
