package ru.clevertec.json.deserializer.parser.json;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
public class JsonNull extends Json<Object> {

    public JsonNull() {
        super(null);
    }
}
