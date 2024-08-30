package ru.clevertec.json.deserializer.parser;

import ru.clevertec.json.deserializer.parser.json.JsonObject;

public interface JsonParser {
    JsonObject parseObject(String json);

}
