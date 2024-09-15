package ru.clevertec.json.mapper;

import ru.clevertec.json.deserializer.Deserializer;
import ru.clevertec.json.deserializer.impl.JsonObjectDeserializer;
import ru.clevertec.json.deserializer.parser.JsonParser;
import ru.clevertec.json.deserializer.parser.JsonParserImpl;
import ru.clevertec.json.deserializer.parser.json.JsonObject;

public class ObjectMapper implements Mapper {
    private final JsonParser jsonParser;
    private final Deserializer deserializer;

    public ObjectMapper() {
        this(new JsonParserImpl(), new JsonObjectDeserializer());
    }

    public ObjectMapper(JsonParser jsonParser, Deserializer deserializer) {
        this.jsonParser = jsonParser;
        this.deserializer = deserializer;
    }

    @Override
    public <T> T read(String json, Class<T> targetClass) {
        JsonObject jsonObject = jsonParser.parseObject(json);
        return deserializer.deserialize(jsonObject, targetClass);
    }
}
