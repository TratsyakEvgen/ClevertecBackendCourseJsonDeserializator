package ru.clevertec.json.deserializer.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.DeserializerFactory;
import ru.clevertec.json.deserializer.parser.json.*;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class JsonArrayDeserializerTest {

    private Deserializer deserializer;
    @Mock
    private DeserializerFactory deserializerFactory;

    @BeforeEach
    void setUp() {
        deserializer = new JsonArrayDeserializer(deserializerFactory);
    }

    @Test
    void deserialize_ifClassIsNotArray() {
        Assertions.assertThrows(DeserializerException.class,
                () -> deserializer.deserialize(new JsonArray(new Json<?>[]{}), String.class)
        );
    }

    @Test
    void deserialize_primitiveArray() {
        JsonArray jsonArray = new JsonArray(new Json<?>[]{new JsonBoolean(true), new JsonBoolean(false)});
        Mockito.when(deserializerFactory.get(Mockito.any())).thenReturn(new JsonBooleanDeserializer());

        boolean[] expected = deserializer.deserialize(jsonArray, boolean[].class);

        Assertions.assertArrayEquals(expected, new boolean[]{true, false});
    }

    @Test
    void deserialize_ObjectArray() {
        JsonArray jsonArray = new JsonArray(new Json<?>[]{new JsonNumber(BigDecimal.ONE), new JsonNumber(BigDecimal.TWO)});
        Mockito.when(deserializerFactory.get(Mockito.any())).thenReturn(new JsonNumberDeserializer());

        Integer[] expected = deserializer.deserialize(jsonArray, Integer[].class);

        Assertions.assertArrayEquals(expected, new Integer[]{1, 2});
    }

    @Test
    void deserialize_ObjectArrayWithIllegalValue() {
        JsonNumber jsonNumber = new JsonNumber(BigDecimal.ONE);
        JsonString jsonString = new JsonString("");
        JsonArray jsonArray = new JsonArray(new Json<?>[]{jsonNumber, jsonString});
        Mockito.when(deserializerFactory.get(jsonNumber)).thenReturn(new JsonNumberDeserializer());
        Mockito.when(deserializerFactory.get(jsonString)).thenReturn(new JsonStringDeserializer());

        Assertions.assertThrows(DeserializerException.class, () -> deserializer.deserialize(jsonArray, Integer[].class));

    }


}