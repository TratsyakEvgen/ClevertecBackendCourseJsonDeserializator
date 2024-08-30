package ru.clevertec.json.deserializer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.json.deserializer.parser.json.JsonBoolean;
import ru.clevertec.json.deserializer.parser.json.JsonString;

@ExtendWith(MockitoExtension.class)
class AbstractDeserializerTest {

    private Deserializer deserializer;

    @BeforeEach
    void setUp() {
        deserializer = new AbstractDeserializer<>(JsonBoolean.class) {
            @Override
            protected <T> T deserializeJson(JsonBoolean json, Class<T> targetClass) {
                return null;
            }
        };
    }

    @Test
    void deserialize_ifIllegalJson() {
        Assertions.assertThrows(DeserializerException.class, () -> deserializer.deserialize(new JsonString(""), Boolean.class));
    }

    @Test
    void deserialize_ifJsonIsNull() {
        Assertions.assertThrows(DeserializerException.class, () -> deserializer.deserialize(null, Boolean.class));
    }

    @Test
    void deserialize_ifClassIsNull() {
        Assertions.assertThrows(DeserializerException.class, () -> deserializer.deserialize(new JsonBoolean(true), null));
    }
}