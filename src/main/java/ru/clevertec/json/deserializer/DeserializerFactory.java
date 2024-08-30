package ru.clevertec.json.deserializer;

import ru.clevertec.json.deserializer.parser.json.Json;

public interface DeserializerFactory {
    Deserializer get(Json<?> json);
}
