package ru.clevertec.json.deserializer.field.impl;

import ru.clevertec.json.deserializer.field.FieldScanner;
import ru.clevertec.json.deserializer.field.annotation.JsonField;
import ru.clevertec.json.deserializer.field.exception.FieldScannerException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FieldScannerImpl implements FieldScanner {
    @Override
    public Map<String, Field> scanJsonName(Class<?> targetClass) {

        if (targetClass == null) {
            throw new FieldScannerException("Class is null");
        }

        return Arrays.stream(targetClass.getDeclaredFields())
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .collect(Collectors.toMap(this::getJsonName, Function.identity()));

    }

    private String getJsonName(Field field) {
        return Optional.ofNullable(field.getAnnotation(JsonField.class))
                .map(JsonField::name)
                .orElseGet(field::getName);

    }
}
