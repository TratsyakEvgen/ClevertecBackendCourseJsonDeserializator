package ru.clevertec.json.deserializer.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonBoolean;

import java.util.stream.Stream;

class JsonBooleanDeserializerTest {

    private Deserializer deserializer;

    @BeforeEach
    void setUp() {
        deserializer = new JsonBooleanDeserializer();
    }

    @Test
    void deserialize_ifClassIsNotBoolean() {
        Assertions.assertThrows(DeserializerException.class, () -> deserializer.deserialize(new JsonBoolean(true), String.class));
    }

    @ParameterizedTest
    @MethodSource("provideClassForDeserialize")
    void deserialize(Class<?> targetClass, boolean value) {
        Assertions.assertEquals(value, deserializer.deserialize(new JsonBoolean(value), targetClass));
    }

    private static Stream<Arguments> provideClassForDeserialize() {
        return Stream.of(
                Arguments.of(Boolean.class, true),
                Arguments.of(boolean.class, true),
                Arguments.of(Boolean.class, false),
                Arguments.of(boolean.class, false)
        );
    }
}