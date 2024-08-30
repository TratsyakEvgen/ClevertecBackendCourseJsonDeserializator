package ru.clevertec.json.deserializer.parser;


import ru.clevertec.json.deserializer.parser.json.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParserImpl implements JsonParser {
    //private final static String REGEX_KEY = "^\".+?\":";
    private final static String REGEX_NUMBER = "^\\d+(\\.\\d+)?";
    private final static String REGEX_STRING = "^\".+?\"";
    private final static String REGEX_NULL = "^null";
    private final static String REGEX_BOOLEAN = "^(true|false)";
    private final static String REGEX_OBJECT = "^\\{.+?}";
    private final static String REGEX_ARRAY = "^\\[.*?]";
    private final static String REGEX_CLOSE = "[:,}\\]]+";


    private final Map<Predicate<String>, Supplier<Json<?>>> jsonComponentSuppliersMap;

    {
        jsonComponentSuppliersMap = new HashMap<>();
        jsonComponentSuppliersMap.put(json -> isMatches(json, REGEX_NUMBER + REGEX_CLOSE), this::getJsonNumber);
        jsonComponentSuppliersMap.put(json -> isMatches(json, REGEX_STRING + REGEX_CLOSE), this::getJsonString);
        jsonComponentSuppliersMap.put(json -> isMatches(json, REGEX_NULL + REGEX_CLOSE), this::getJsonNull);
        jsonComponentSuppliersMap.put(json -> isMatches(json, REGEX_BOOLEAN + REGEX_CLOSE), this::getJsonBoolean);
        jsonComponentSuppliersMap.put(json -> isMatches(json, REGEX_OBJECT + REGEX_CLOSE), this::getJsonObject);
        jsonComponentSuppliersMap.put(json -> isMatches(json, REGEX_ARRAY + REGEX_CLOSE), this::getJsonArray);
    }

    private String json;


    @Override
    public JsonObject parseObject(String json) {
        this.json = json;
        valid();
        this.json = this.json.substring(1);
        Map<String, Json<?>> jsonComponentMap = new HashMap<>();

        while (!this.json.isEmpty()) {
            String key = getAndRemoveString();
            Json<?> value = getAndRemoveValue();
            jsonComponentMap.put(key, value);
        }
        return new JsonObject(jsonComponentMap);
    }

    private boolean isMatches(String json, String regex) {
        return Pattern.compile(regex).matcher(json).find();
    }


    private JsonArray parseArray(String json) {
        this.json = json.substring(1);
        List<Json<?>> jsonList = new ArrayList<>();

        while (!this.json.isEmpty()) {
            jsonList.add(getAndRemoveValue());
        }
        return new JsonArray(jsonList.toArray(new Json[0]));
    }


    private String getAndRemoveString() {
        String key = getAndRemoveJsonSubstring(REGEX_STRING, REGEX_STRING + REGEX_CLOSE);
        key = key.substring(1, key.length() - 1);
        return key;
    }

    private Json<?> getAndRemoveValue() {
        return jsonComponentSuppliersMap.entrySet().stream()
                .filter(entry -> entry.getKey().test(json))
                .map(Map.Entry::getValue)
                .map(Supplier::get)
                .findFirst()
                .orElseThrow(() -> new JsonParserException("Incorrect format json, Cannot read value " + json));
    }

    private JsonNumber getJsonNumber() {
        String value = getAndRemoveJsonSubstring(REGEX_NUMBER, REGEX_NUMBER + REGEX_CLOSE);
        return new JsonNumber(new BigDecimal(value));
    }


    private JsonString getJsonString() {
        String value = getAndRemoveString();
        return new JsonString(value);
    }

    private JsonNull getJsonNull() {
        json = json.replaceFirst(REGEX_NULL + REGEX_CLOSE, "");
        return new JsonNull();
    }

    private JsonBoolean getJsonBoolean() {
        String value = getAndRemoveJsonSubstring(REGEX_BOOLEAN, REGEX_BOOLEAN + REGEX_CLOSE);
        return new JsonBoolean(Boolean.valueOf(value));
    }

    private JsonObject getJsonObject() {
        String value = getAndRemoveJsonSubstring(REGEX_OBJECT, REGEX_OBJECT + REGEX_CLOSE);
        return new JsonParserImpl().parseObject(value);
    }

    private JsonArray getJsonArray() {
        String value = getAndRemoveJsonSubstring(REGEX_ARRAY, REGEX_ARRAY + REGEX_CLOSE);
        return new JsonParserImpl().parseArray(value);
    }


    private void valid() {
        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new JsonParserException(
                    String.format("Incorrect format json: must start with %s and end %s", "{", "}")
            );
        }
    }

    private String getAndRemoveJsonSubstring(String regexSubstring, String regexReplace) {
        Pattern pattern = Pattern.compile(regexSubstring);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            String substring = json.substring(matcher.start(), matcher.end());
            json = json.replaceFirst(regexReplace, "");
            return substring;
        }

        throw new JsonParserException(
                String.format("Incorrect format json! Cannot parse part json %s with regex %s ", json, REGEX_STRING)
        );
    }


}
