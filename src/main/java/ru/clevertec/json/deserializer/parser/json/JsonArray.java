package ru.clevertec.json.deserializer.parser.json;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
public class JsonArray extends Json<Json<?>[]> {

    public JsonArray(Json<?>[] value) {
        super(value);
    }
}
