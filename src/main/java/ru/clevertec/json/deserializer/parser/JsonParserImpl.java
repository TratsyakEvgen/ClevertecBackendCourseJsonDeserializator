package ru.clevertec.json.deserializer.parser;

import ru.clevertec.json.deserializer.parser.json.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParserImpl implements JsonParser {
    private final static String REGEX_NUMBER = "^\\d+(\\.\\d+)?";
    private final static String REGEX_STRING = "^\".+?\"";
    private final static String REGEX_NULL = "^null";
    private final static String REGEX_BOOLEAN = "^(true|false)";
    private final static String REGEX_OBJECT = "^\\{.+?}";
    private final static String REGEX_ARRAY = "^\\[.*?]";
    private final static String REGEX_CLOSE = "[:,}\\]]+";

    private final List<String> regexList =
            List.of(REGEX_NUMBER, REGEX_STRING, REGEX_NULL, REGEX_BOOLEAN, REGEX_OBJECT, REGEX_ARRAY);
    private final Map<String, Function<String, Json<?>>> jsonComponentSuppliersMap;

    {
        jsonComponentSuppliersMap = new HashMap<>();
        jsonComponentSuppliersMap.put(REGEX_NUMBER, json -> new JsonNumber(new BigDecimal(json)));
        jsonComponentSuppliersMap.put(REGEX_STRING, JsonString::new);
        jsonComponentSuppliersMap.put(REGEX_NULL, json -> new JsonNull());
        jsonComponentSuppliersMap.put(REGEX_BOOLEAN, json -> new JsonBoolean(Boolean.valueOf(json)));
        jsonComponentSuppliersMap.put(REGEX_OBJECT, json -> new JsonParserImpl().parseObject(json));
        jsonComponentSuppliersMap.put(REGEX_ARRAY, json -> new JsonParserImpl().parseArray(json));
    }

    @Override
    public JsonObject parseObject(String json) {
        valid(json);
        json = json.substring(1);
        Map<String, Json<?>> jsonComponentMap = new HashMap<>();

        while (!json.isEmpty()) {
            String key = getJsonSubstring(REGEX_STRING, json);
            key = key.substring(1, key.length() - 1);
            json = json.replaceFirst(REGEX_STRING + REGEX_CLOSE, "");

            String regex = findRegex(json);
            String jsonSubstring = getJsonSubstring(regex, json);
            json = json.replaceFirst(regex + REGEX_CLOSE, "");
            Json<?> value = getValue(regex, jsonSubstring);
            jsonComponentMap.put(key, value);
        }
        return new JsonObject(jsonComponentMap);
    }

    private JsonArray parseArray(String json) {
        json = json.substring(1);
        List<Json<?>> jsonList = new ArrayList<>();

        while (!json.isEmpty()) {
            String regex = findRegex(json);
            String jsonSubstring = getJsonSubstring(regex, json);
            json = json.replaceFirst(regex + REGEX_CLOSE, "");
            jsonList.add(getValue(regex, jsonSubstring));
        }
        return new JsonArray(jsonList.toArray(new Json[0]));
    }

    private String findRegex(String json) {
        return regexList.stream()
                .filter(regex -> isMatches(json, regex + REGEX_CLOSE))
                .findFirst()
                .orElseThrow(() -> new JsonParserException("Incorrect format json, Cannot read value " + json));
    }

    private boolean isMatches(String json, String regex) {
        return Pattern.compile(regex).matcher(json).find();
    }

    private Json<?> getValue(String regex, String json) {
        return jsonComponentSuppliersMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals(regex))
                .map(Map.Entry::getValue)
                .map(function -> function.apply(json))
                .findFirst()
                .orElseThrow(() -> new JsonParserException("Incorrect format json, Cannot read value " + json));
    }

    private void valid(String json) {
        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new JsonParserException("Incorrect format json: must start with { and end }");
        }
    }

    private String getJsonSubstring(String regex, String json) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return json.substring(matcher.start(), matcher.end());
        }

        throw new JsonParserException(
                String.format("Incorrect format json! Cannot parse part json %s with regex %s ", json, regex)
        );
    }
}
