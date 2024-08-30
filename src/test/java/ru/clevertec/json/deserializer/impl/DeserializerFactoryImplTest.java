package ru.clevertec.json.deserializer.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.DeserializerFactory;
import ru.clevertec.json.deserializer.parser.json.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.stream.Stream;

class DeserializerFactoryImplTest {
    private DeserializerFactory deserializerFactory;

    @BeforeEach
    void setUp() {
        deserializerFactory = new DeserializerFactoryImpl();
    }

    @ParameterizedTest
    @MethodSource("provideJson")
    void get(Json<?> json, Deserializer deserializer) {
        Assertions.assertEquals(deserializer.getClass(), deserializerFactory.get(json).getClass());
    }

    private static Stream<Arguments> provideJson() {
        return Stream.of(
                Arguments.of(new JsonString(""), new JsonStringDeserializer()),
                Arguments.of(new JsonArray(new Json[]{null}), new JsonArrayDeserializer()),
                Arguments.of(new JsonNull(), new JsonNullDeserializer()),
                Arguments.of(new JsonNumber(BigDecimal.ONE), new JsonNumberDeserializer()),
                Arguments.of(new JsonBoolean(true), new JsonBooleanDeserializer()),
                Arguments.of(new JsonObject(new HashMap<>()), new JsonObjectDeserializer())
        );
    }
}