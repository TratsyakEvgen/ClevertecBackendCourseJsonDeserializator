package ru.clevertec.json.deserializer.impl;

import ru.clevertec.json.deserializer.AbstractDeserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonNull;

public class JsonNullDeserializer extends AbstractDeserializer<JsonNull> {

    public JsonNullDeserializer() {
        super(JsonNull.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserializeJson(JsonNull json, Class<T> targetClass) {
        if (targetClass.isPrimitive()) {
            throw new DeserializerException(String.format("Class %s is primitive", targetClass));
        }
        return (T) json.getValue();
    }
}
