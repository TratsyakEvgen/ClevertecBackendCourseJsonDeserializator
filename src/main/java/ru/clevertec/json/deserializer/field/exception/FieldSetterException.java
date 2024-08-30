package ru.clevertec.json.deserializer.field.exception;

public class FieldSetterException extends RuntimeException {

    public FieldSetterException(String message) {
        super(message);
    }

    public FieldSetterException(String message, Throwable cause) {
        super(message, cause);
    }
}
