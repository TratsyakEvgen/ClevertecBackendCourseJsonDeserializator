package ru.clevertec.json.deserializer.impl;

import ru.clevertec.json.deserializer.AbstractDeserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonString;

public class JsonStringDeserializer extends AbstractDeserializer<JsonString> {

    public JsonStringDeserializer() {
        super(JsonString.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T deserializeJson(JsonString json, Class<T> targetClass) {
        if (targetClass == char.class || targetClass == Character.class) {
            return (T) Character.valueOf(json.getValue().charAt(0));
        }
        if (targetClass == String.class) {
            return (T) json.getValue();
        }

        throw new DeserializerException(String.format("Cannot convert json %s to %s", json, targetClass));
    }
}
