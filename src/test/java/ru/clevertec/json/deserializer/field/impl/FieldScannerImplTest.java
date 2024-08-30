package ru.clevertec.json.deserializer.field.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.TestingClass;
import ru.clevertec.json.deserializer.field.FieldScanner;
import ru.clevertec.json.deserializer.field.exception.FieldScannerException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FieldScannerImplTest {

    private FieldScanner fieldScanner;

    @BeforeEach
    void setUp() {
        fieldScanner = new FieldScannerImpl();
    }

    @Test
    void scanJsonName_ifClassIsNull() {
        assertThrows(FieldScannerException.class, () -> fieldScanner.scanJsonName(null));
    }

    @Test
    void scanJsonName() {
        Map<String, String> actualMapNameField = new HashMap<>();

        actualMapNameField.put("someString", "stringField");
        actualMapNameField.put("charField", "charField");
        actualMapNameField.put("byteField", "byteField");
        actualMapNameField.put("shortField", "shortField");
        actualMapNameField.put("intField", "intField");
        actualMapNameField.put("longField", "longField");
        actualMapNameField.put("floatField", "floatField");
        actualMapNameField.put("doubleField", "doubleField");

        actualMapNameField.put("wrapperCharField", "wrapperCharField");
        actualMapNameField.put("wrapperByteField", "wrapperByteField");
        actualMapNameField.put("wrapperShortField", "wrapperShortField");
        actualMapNameField.put("wrapperIntField", "wrapperIntField");
        actualMapNameField.put("wrapperLongField", "wrapperLongField");
        actualMapNameField.put("wrapperFloatField", "wrapperFloatField");
        actualMapNameField.put("wrapperDoubleField", "wrapperDoubleField");

        actualMapNameField.put("objectField", "objectField");
        actualMapNameField.put("arrayObjectField", "arrayObjectField");

        actualMapNameField.put("charArrayField", "charArrayField");
        actualMapNameField.put("byteArrayField", "byteArrayField");
        actualMapNameField.put("shortArrayField", "shortArrayField");
        actualMapNameField.put("intArrayField", "intArrayField");
        actualMapNameField.put("longArrayField", "longArrayField");
        actualMapNameField.put("floatArrayField", "floatArrayField");
        actualMapNameField.put("doubleArrayField", "doubleArrayField");

        actualMapNameField.put("wrapperCharArrayField", "wrapperCharArrayField");
        actualMapNameField.put("wrapperByteArrayField", "wrapperByteArrayField");
        actualMapNameField.put("wrapperShortArrayField", "wrapperShortArrayField");
        actualMapNameField.put("wrapperIntArrayField", "wrapperIntArrayField");
        actualMapNameField.put("wrapperLongArrayField", "wrapperLongArrayField");
        actualMapNameField.put("wrapperFloatArrayField", "wrapperFloatArrayField");
        actualMapNameField.put("wrapperDoubleArrayField", "wrapperDoubleArrayField");


        Map<String, Field> expectedMapNameField = fieldScanner.scanJsonName(TestingClass.class);


        assertEquals(expectedMapNameField.keySet(), actualMapNameField.keySet());
        assertEquals(expectedMapNameField.values().stream().map(Field::getName).collect(Collectors.toSet()),
                new HashSet<>(actualMapNameField.values())
        );
    }
}