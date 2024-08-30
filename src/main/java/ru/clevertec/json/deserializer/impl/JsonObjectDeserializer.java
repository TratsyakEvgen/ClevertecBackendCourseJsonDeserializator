package ru.clevertec.json.deserializer.impl;

import ru.clevertec.json.deserializer.AbstractDeserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.DeserializerFactory;
import ru.clevertec.json.deserializer.field.FieldScanner;
import ru.clevertec.json.deserializer.field.FieldSetter;
import ru.clevertec.json.deserializer.field.impl.FieldScannerImpl;
import ru.clevertec.json.deserializer.field.impl.FieldSetterImpl;
import ru.clevertec.json.deserializer.parser.json.Json;
import ru.clevertec.json.deserializer.parser.json.JsonObject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;

public class JsonObjectDeserializer extends AbstractDeserializer<JsonObject> {
    private final FieldScanner fieldScanner;

    private final DeserializerFactory deserializerFactory;
    private final FieldSetter fieldSetter;

    public JsonObjectDeserializer() {
        this(new FieldScannerImpl(), new DeserializerFactoryImpl(), new FieldSetterImpl());
    }

    public JsonObjectDeserializer(FieldScanner fieldScanner, DeserializerFactory deserializerFactory, FieldSetter fieldSetter) {
        super(JsonObject.class);
        this.fieldScanner = fieldScanner;
        this.deserializerFactory = deserializerFactory;
        this.fieldSetter = fieldSetter;
    }


    @Override
    @SuppressWarnings("unchecked")
    protected <T> T deserializeJson(JsonObject jsonObject, Class<T> targetClass) {
        Object object = creteNewInstance(targetClass);

        Map<String, Field> fieldMap = fieldScanner.scanJsonName(targetClass);
        Map<String, Json<?>> jsonMap = jsonObject.getValue();

        jsonMap.forEach((name, json) -> {
            Field field = Optional.ofNullable(fieldMap.get(name))
                    .orElseThrow(() ->
                            new DeserializerException(
                                    String.format("Not found field with name %s in class %s", name, targetClass)
                            )
                    );


            Object value = deserializerFactory.get(json).deserialize(json, field.getType());


            fieldSetter.set(object, field, value);
        });

        return (T) object;
    }


    private Object creteNewInstance(Class<?> targetClass) {
        try {
            return targetClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new DeserializerException("Cannot create object " + targetClass);
        }
    }


}
