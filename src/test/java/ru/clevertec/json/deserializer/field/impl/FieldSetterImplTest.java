package ru.clevertec.json.deserializer.field.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.clevertec.json.TestingClass;
import ru.clevertec.json.deserializer.field.FieldSetter;
import ru.clevertec.json.deserializer.field.exception.FieldSetterException;

import java.lang.reflect.Field;
import java.util.stream.Stream;

class FieldSetterImplTest {

    private FieldSetter fieldSetter;
    private TestingClass testingClass;

    @BeforeEach
    void setUp() {
        fieldSetter = new FieldSetterImpl();
        testingClass = new TestingClass();
    }

    @ParameterizedTest
    @MethodSource("provideNullForSet")
    void set_ifObjectOrFieldIsNull(Object object, Field field) {

        Assertions.assertThrows(FieldSetterException.class, () -> fieldSetter.set(object, field, null));
    }

    @ParameterizedTest
    @MethodSource("provideFieldAndValueForSet")
    void set(Field field, Object value) throws IllegalAccessException {

        fieldSetter.set(testingClass, field, value);

        field.setAccessible(true);

        Assertions.assertEquals(field.get(testingClass), value);
        field.setAccessible(false);
    }


    @ParameterizedTest
    @MethodSource("provideIllegalValueForField")
    void set_ifValueIsIllegalForField(Field field, Object value) {

        Assertions.assertThrows(FieldSetterException.class, () -> fieldSetter.set(testingClass, field, value));
    }

    private static Stream<Arguments> provideNullForSet() throws NoSuchFieldException {
        return Stream.of(
                Arguments.of(null, TestingClass.class.getDeclaredField("intField")),
                Arguments.of(new Object(), null)
        );

    }

    private static Stream<Arguments> provideFieldAndValueForSet() throws NoSuchFieldException {
        return Stream.of(
                Arguments.of(TestingClass.class.getDeclaredField("stringField"), ""),
                Arguments.of(TestingClass.class.getDeclaredField("floatField"), Float.valueOf(1.0F)),
                Arguments.of(TestingClass.class.getDeclaredField("wrapperDoubleField"), 3.0),
                Arguments.of(TestingClass.class.getDeclaredField("arrayObjectField"), new TestingClass[]{new TestingClass()}),
                Arguments.of(TestingClass.class.getDeclaredField("charArrayField"), new char[]{'c'}),
                Arguments.of(TestingClass.class.getDeclaredField("wrapperIntArrayField"), new Integer[]{1})
        );

    }

    private static Stream<Arguments> provideIllegalValueForField() throws NoSuchFieldException {
        return Stream.of(
                Arguments.of(TestingClass.class.getDeclaredField("floatField"), null),
                Arguments.of(TestingClass.class.getDeclaredField("intField"), null)
        );

    }
}