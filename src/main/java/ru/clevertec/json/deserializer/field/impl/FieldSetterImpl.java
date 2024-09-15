package ru.clevertec.json.deserializer.field.impl;

import ru.clevertec.json.deserializer.field.FieldSetter;
import ru.clevertec.json.deserializer.field.exception.FieldSetterException;

import java.lang.reflect.Field;

public class FieldSetterImpl implements FieldSetter {

    @Override
    public void set(Object object, Field field, Object value) {
        validate(object, field);

        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new FieldSetterException(
                    String.format("Cannot set value %s in field %s for object %s", value, field, object), e
            );
        } finally {
            field.setAccessible(false);
        }
    }

    private void validate(Object object, Field field) {
        if (object == null) {
            throw new FieldSetterException("Object mustn't be null");
        }
        if (field == null) {
            throw new FieldSetterException("Field mustn't be null");
        }
    }
}
