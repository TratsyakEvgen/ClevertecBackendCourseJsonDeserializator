package ru.clevertec.json.deserializer.impl;

import ru.clevertec.json.deserializer.AbstractDeserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonBoolean;

public class JsonBooleanDeserializer extends AbstractDeserializer<JsonBoolean> {

    public JsonBooleanDeserializer() {
        super(JsonBoolean.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T deserializeJson(JsonBoolean json, Class<T> targetClass) {
        if (targetClass != Boolean.class && targetClass != boolean.class) {
            throw new DeserializerException(String.format("Class %s isn't boolean", targetClass));
        }
        return (T) json.getValue();
    }
}
