package ru.clevertec.json;

import lombok.Data;
import ru.clevertec.json.deserializer.field.annotation.JsonField;

@Data
public class TestingClass {
    @JsonField(name = "someString")

    private String stringField;
    private char charField;
    private byte byteField;
    private short shortField;
    private int intField;
    private long longField;
    private float floatField;
    private double doubleField;

    private Character wrapperCharField;
    private Byte wrapperByteField;
    private Short wrapperShortField;
    private Integer wrapperIntField;
    private Long wrapperLongField;
    private Float wrapperFloatField;
    private Double wrapperDoubleField;

    private TestingClass objectField;
    private TestingClass[] arrayObjectField;

    private char[] charArrayField;
    private byte[] byteArrayField;
    private short[] shortArrayField;
    private int[] intArrayField;
    private long[] longArrayField;
    private float[] floatArrayField;
    private double[] doubleArrayField;

    private Character[] wrapperCharArrayField;
    private Byte[] wrapperByteArrayField;
    private Short[] wrapperShortArrayField;
    private Integer[] wrapperIntArrayField;
    private Long[] wrapperLongArrayField;
    private Float[] wrapperFloatArrayField;
    private Double[] wrapperDoubleArrayField;

}
