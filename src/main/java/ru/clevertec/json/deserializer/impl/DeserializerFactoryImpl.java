package ru.clevertec.json.deserializer.impl;

import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerFactory;
import ru.clevertec.json.deserializer.parser.json.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class DeserializerFactoryImpl implements DeserializerFactory {
    private final static Map<Class<?>, Supplier<Deserializer>> JSON_SUPPLIER_MAP;

    static {
        JSON_SUPPLIER_MAP = new HashMap<>();
        JSON_SUPPLIER_MAP.put(JsonArray.class, JsonArrayDeserializer::new);
        JSON_SUPPLIER_MAP.put(JsonNull.class, JsonNullDeserializer::new);
        JSON_SUPPLIER_MAP.put(JsonNumber.class, JsonNumberDeserializer::new);
        JSON_SUPPLIER_MAP.put(JsonBoolean.class, JsonBooleanDeserializer::new);
        JSON_SUPPLIER_MAP.put(JsonString.class, JsonStringDeserializer::new);
    }

    @Override
    public Deserializer get(Json<?> json) {
        return Optional.ofNullable(json)
                .map(Json::getClass)
                .map(JSON_SUPPLIER_MAP::get)
                .map(Supplier::get)
                .orElseGet(JsonObjectDeserializer::new);
    }
}
