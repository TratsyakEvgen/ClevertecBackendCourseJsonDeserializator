package ru.clevertec.json.deserializer.parser.json;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ToString
public class JsonNumber extends Json<BigDecimal> {

    public JsonNumber(BigDecimal value) {
        super(value);
    }
}
