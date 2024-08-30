package ru.clevertec.json.deserializer;

public class DeserializerException extends RuntimeException {
    public DeserializerException(String message) {
        super(message);
    }

    public DeserializerException(String message, Throwable cause) {
        super(message, cause);
    }
}
