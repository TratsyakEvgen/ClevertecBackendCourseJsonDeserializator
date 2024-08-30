package ru.clevertec.json.deserializer.parser.json;


import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@ToString
public class JsonObject extends Json<Map<String, Json<?>>> {
    public JsonObject(Map<String, Json<?>> value) {
        super(value);
    }
}
