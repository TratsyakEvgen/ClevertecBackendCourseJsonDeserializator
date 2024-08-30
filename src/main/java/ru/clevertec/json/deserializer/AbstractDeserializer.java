package ru.clevertec.json.deserializer;

import ru.clevertec.json.deserializer.parser.json.Json;

public abstract class AbstractDeserializer<J extends Json<?>> implements Deserializer {

    private final Class<J> genericClass;

    protected AbstractDeserializer(Class<J> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialize(Json<?> json, Class<T> targetClass) {
        validate(json, targetClass);
        J someJson = (J) json;
        return deserializeJson(someJson, targetClass);
    }

    private void validate(Json<?> json, Class<?> targetClass) {
        if (json == null) {
            throw new DeserializerException("Json mustn't be null");
        }
        if (targetClass == null) {
            throw new DeserializerException("Class mustn't be null");
        }
        if (json.getClass() != genericClass) {
            throw new DeserializerException(String.format("Cannot cast json %s to class %s", json, genericClass));
        }
    }

    protected abstract <T> T deserializeJson(J json, Class<T> targetClass);
}
