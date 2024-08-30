package ru.clevertec.json.deserializer.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.json.TestingClass;
import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonString;

class JsonStringDeserializerTest {

    private Deserializer deserializer;

    @BeforeEach
    void setUp() {
        deserializer = new JsonStringDeserializer();
    }

    @Test
    void deserialize_ifClassIsNotStringOrCharRepresentative() {
        Assertions.assertThrows(DeserializerException.class,
                () -> deserializer.deserialize(new JsonString(""), TestingClass.class)
        );
    }

    @Test
    void deserialize_ifClassIsString() {
        String expected = deserializer.deserialize(new JsonString("some"), String.class);

        Assertions.assertEquals(expected, "some");
    }

    @Test
    void deserialize_ifClassIsChar() {
        char expected = deserializer.deserialize(new JsonString("s"), char.class);

        Assertions.assertEquals(expected, 's');
    }

    @Test
    void deserialize_ifClassIsCharacter() {
        Character expected = deserializer.deserialize(new JsonString("s"), Character.class);

        Assertions.assertEquals(expected, 's');
    }


}