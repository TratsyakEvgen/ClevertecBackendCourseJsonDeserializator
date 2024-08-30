package ru.clevertec.json.deserializer.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.json.TestingClass;
import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.DeserializerFactory;
import ru.clevertec.json.deserializer.field.FieldScanner;
import ru.clevertec.json.deserializer.field.FieldSetter;
import ru.clevertec.json.deserializer.parser.json.Json;
import ru.clevertec.json.deserializer.parser.json.JsonNumber;
import ru.clevertec.json.deserializer.parser.json.JsonObject;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class JsonObjectDeserializerTest {
    private Deserializer deserializer;
    @Mock
    private FieldScanner fieldScanner;
    @Mock
    private DeserializerFactory deserializerFactory;
    @Mock
    private FieldSetter fieldSetter;

    @BeforeEach
    void setUp() {
        deserializer = new JsonObjectDeserializer(fieldScanner, deserializerFactory, fieldSetter);
    }

    @Test
    void deserialize_ifClassHasNotDefaultConstructor() {
        Assertions.assertThrows(DeserializerException.class,
                () -> deserializer.deserialize(new JsonObject(new HashMap<>()), Integer.class));
    }

    @Test
    void deserialize_ifClassHasNotFieldWithName() throws NoSuchFieldException {
        Map<String, Field> fieldNameMap = new HashMap<>();
        fieldNameMap.put("someField", TestingClass.class.getDeclaredField("intField"));
        Map<String, Json<?>> jsonMap = new HashMap<>();
        jsonMap.put("intField", new JsonNumber(BigDecimal.ONE));
        JsonObject jsonObject = new JsonObject(jsonMap);

        Mockito.when(fieldScanner.scanJsonName(TestingClass.class)).thenReturn(fieldNameMap);


        Assertions.assertThrows(DeserializerException.class,
                () -> deserializer.deserialize(jsonObject, TestingClass.class));
    }

}