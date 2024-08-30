package ru.clevertec.json.deserializer.parser.json;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
public class JsonBoolean extends Json<Boolean> {
    public JsonBoolean(Boolean value) {
        super(value);
    }

}
