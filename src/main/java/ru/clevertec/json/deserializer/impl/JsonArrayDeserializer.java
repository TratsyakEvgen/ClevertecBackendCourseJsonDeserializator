package ru.clevertec.json.deserializer.impl;

import ru.clevertec.json.deserializer.AbstractDeserializer;
import ru.clevertec.json.deserializer.DeserializerException;
import ru.clevertec.json.deserializer.DeserializerFactory;
import ru.clevertec.json.deserializer.parser.json.Json;
import ru.clevertec.json.deserializer.parser.json.JsonArray;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonArrayDeserializer extends AbstractDeserializer<JsonArray> {
    private final static Map<Class<?>, ArraySetter> CLASS_ARRAY_SETTER_MAP;

    static {
        CLASS_ARRAY_SETTER_MAP = new HashMap<>();
        CLASS_ARRAY_SETTER_MAP.put(byte.class, ((object, index, value) -> Array.setByte(object, index, (Byte) value)));
        CLASS_ARRAY_SETTER_MAP.put(short.class, ((object, index, value) -> Array.setShort(object, index, (Short) value)));
        CLASS_ARRAY_SETTER_MAP.put(char.class, ((object, index, value) -> Array.setChar(object, index, (Character) value)));
        CLASS_ARRAY_SETTER_MAP.put(int.class, ((object, index, value) -> Array.setInt(object, index, (Integer) value)));
        CLASS_ARRAY_SETTER_MAP.put(long.class, ((object, index, value) -> Array.setLong(object, index, (Long) value)));
        CLASS_ARRAY_SETTER_MAP.put(double.class, ((object, index, value) -> Array.setDouble(object, index, (Double) value)));
        CLASS_ARRAY_SETTER_MAP.put(float.class, ((object, index, value) -> Array.setFloat(object, index, (Float) value)));
        CLASS_ARRAY_SETTER_MAP.put(boolean.class, ((object, index, value) -> Array.setBoolean(object, index, (Boolean) value)));
    }

    private final DeserializerFactory deserializerFactory;

    public JsonArrayDeserializer() {
        this(new DeserializerFactoryImpl());
    }

    public JsonArrayDeserializer(DeserializerFactory deserializerFactory) {
        super(JsonArray.class);
        this.deserializerFactory = deserializerFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T deserializeJson(JsonArray json, Class<T> targetClass) {
        validate(targetClass);

        Json<?>[] jsons = json.getValue();

        Class<?> component = targetClass.getComponentType();
        Object objects = Array.newInstance(component, jsons.length);

        setValueInArray(component, jsons, objects);

        return (T) objects;
    }

    private void validate(Class<?> targetClass) {
        if (!targetClass.isArray()) {
            throw new DeserializerException(String.format("Class %s isn't array", targetClass));
        }
    }

    private void setValueInArray(Class<?> component, Json<?>[] jsons, Object objects) {
        ArraySetter arraySetter = getArraySetter(component);
        for (int i = 0; i < jsons.length; i++) {
            Object value = deserializerFactory.get(jsons[i]).deserialize(jsons[i], component);
            try {
                arraySetter.set(objects, i, value);
            } catch (IllegalArgumentException e) {
                throw new DeserializerException(String.format("Value %s isn't %s", value, component), e);
            }
        }
    }

    private ArraySetter getArraySetter(Class<?> componentType) {
        return Optional.ofNullable(CLASS_ARRAY_SETTER_MAP.get(componentType)).orElseGet(() -> (Array::set));
    }

    private interface ArraySetter {
        void set(Object object, int index, Object value);
    }
}
