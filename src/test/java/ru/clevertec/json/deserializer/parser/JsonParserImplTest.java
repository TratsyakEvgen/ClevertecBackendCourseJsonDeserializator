package ru.clevertec.json.deserializer.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.clevertec.json.deserializer.parser.json.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

class JsonParserImplTest {

    private JsonParser jsonParser;

    @BeforeEach
    void setUp() {
        jsonParser = new JsonParserImpl();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{\"name\":\"someValue\"",
            "\"name\":\"someValue\"}",
            "{name\":\"someValue\"}",
            "{\"name:\"someValue\"}",
            "{\"name\":someValue\"}",
            "{\"name\":\"someValue}",
            "{\"name\": 1}",
            "{\"name\":1 }",
            "{\"name\":\"name\":\"someValue\"}}",
            "{\"name\":[{\"name\":\"someValue\"}}}",

    })
    void parse_ifNotValidJson(String json) {
        Assertions.assertThrows(JsonParserException.class, () -> jsonParser.parseObject(json));
    }

    @Test
    void parse_ifValidJson() {

        Map<String, Json<?>> actualMap = new HashMap<>();
        actualMap.put("number", new JsonNumber(BigDecimal.ONE));
        actualMap.put("boolean", new JsonBoolean(true));
        actualMap.put("null", new JsonNull());
        Map<String, Json<?>> object = new HashMap<>();
        object.put("someNumber", new JsonNumber(BigDecimal.ONE));
        actualMap.put("object", new JsonObject(object));
        actualMap.put("array", new JsonArray(new Json<?>[]{new JsonNumber(BigDecimal.ONE), new JsonNumber(BigDecimal.TWO)}));
        JsonObject actual = new JsonObject(actualMap);

        String json = "{\"number\":1,\"boolean\":true,\"null\":null,\"object\":{\"someNumber\":1},\"array\":[1,2]}";
        JsonObject expected = jsonParser.parseObject(json);


        Assertions.assertEquals(expected, actual);
    }
}