package ru.clevertec.json.deserializer.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonNull;

class JsonNullDeserializerTest {

    private Deserializer deserializer;

    @BeforeEach
    void setUp() {
        deserializer = new JsonNullDeserializer();
    }

    @Test
    void deserialize_ifClassIsPrimitive() {
        Assertions.assertThrows(DeserializerException.class, () -> deserializer.deserialize(new JsonNull(), int.class));
    }

    @Test
    void deserialize() {
        Assertions.assertNull(deserializer.deserialize(new JsonNull(), String.class));
    }
}