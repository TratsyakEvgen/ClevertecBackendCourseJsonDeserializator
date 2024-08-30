package ru.clevertec.json.deserializer;

import ru.clevertec.json.deserializer.parser.json.Json;

public interface Deserializer {
    <T> T deserialize(Json<?> json, Class<T> targetClass);

}
