package ru.clevertec.json.deserializer.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.TestingClass;
import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonNumber;

import java.math.BigDecimal;

class JsonNumberDeserializerTest {

    private Deserializer deserializer;

    @BeforeEach
    void setUp() {
        deserializer = new JsonNumberDeserializer();
    }

    @Test
    void deserialize_ifClassIsNotNumberRepresentative() {
        Assertions.assertThrows(DeserializerException.class,
                () -> deserializer.deserialize(new JsonNumber(BigDecimal.ONE), TestingClass.class)
        );
    }

    @Test
    void deserialize_ifClassIsPrimitive() {
        int i = deserializer.deserialize(new JsonNumber(BigDecimal.ONE), int.class);

        Assertions.assertEquals(i, 1);
    }

    @Test
    void deserialize_ifClassIsNumber() {
        Float f = deserializer.deserialize(new JsonNumber(new BigDecimal("2.2")), Float.class);

        Assertions.assertEquals(f, 2.2F);
    }
}