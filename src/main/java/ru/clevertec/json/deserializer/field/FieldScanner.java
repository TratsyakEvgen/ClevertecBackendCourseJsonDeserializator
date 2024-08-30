package ru.clevertec.json.deserializer.field;

import java.lang.reflect.Field;
import java.util.Map;

public interface FieldScanner {
    Map<String, Field> scanJsonName(Class<?> targetClass);
}
