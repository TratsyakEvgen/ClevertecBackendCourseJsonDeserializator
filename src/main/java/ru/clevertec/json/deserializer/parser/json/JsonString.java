package ru.clevertec.json.deserializer.parser.json;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
public class JsonString extends Json<String> {
    public JsonString(String value) {
        super(value);
    }

}
