package ru.clevertec.json.deserializer.impl;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.NumberConverter;
import ru.clevertec.json.deserializer.AbstractDeserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.parser.json.JsonNumber;

public class JsonNumberDeserializer extends AbstractDeserializer<JsonNumber> {
    private final NumberConverter numberConverter;

    public JsonNumberDeserializer() {
        this(new BigDecimalConverter());
    }

    public JsonNumberDeserializer(NumberConverter numberConverter) {
        super(JsonNumber.class);
        this.numberConverter = numberConverter;
    }

    @Override
    protected <T> T deserializeJson(JsonNumber json, Class<T> targetClass) {
        try {
            return numberConverter.convert(targetClass, json.getValue());
        } catch (ConversionException e) {
            throw new DeserializerException(String.format("Cannot convert json %s to %s", json, targetClass), e);
        }

    }
}
