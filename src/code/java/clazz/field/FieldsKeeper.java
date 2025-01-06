package code.java.clazz.field;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;
import java.util.*;

import static code.java.clazz.utils.ClassUtils.isNormalClass;

/**
 * 类、类对象、对象的非静态字段保持者。
 * <p>
 * A variable with a type, name, and value.
 */
public class FieldsKeeper {

    private Class<?> clz;
    private String name;
    private Object value;

    private List<Field> nonStaticFieldList = new ArrayList<>();

    /**
     * Construct a variable.
     *
     * @param clz   the type
     * @param name  the name
     * @param value the value
     */
    public FieldsKeeper(Class<?> clz, String name, @Nullable Object value) {
        this.clz = clz;
        this.name = name;
        this.value = value;
        findAllNonStaticFields(value);
    }

    private void findAllNonStaticFields(Object value) {

        // find all fields if we have a class type
        // except we don't expand strings and null values
        if (!isNormalClass(clz) || value == null) {
            return;
        }

        // get fields from the class and all superclasses
        for (Class<?> c = value.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fieldArr = c.getDeclaredFields();
            AccessibleObject.setAccessible(fieldArr, true);
            // get all nonstatic fields
            for (Field f : fieldArr) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    nonStaticFieldList.add(f);
                }
            }
        }

    }

    /**
     * Gets the value of this variable.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Gets all nonstatic fields of this variable.
     *
     * @return an array list of variables describing the fields
     */
    public List<Field> getNonStaticFieldList() {
        return nonStaticFieldList;
    }

    //   类字符串：类名 字段名 = 值
    // 字段字符串：类名 字段名 = 值
    @Override
    public String toString() {
        String r = clz + " " + name;
        if (clz.isPrimitive() || clz.equals(String.class)) {
            r += "=" + value;
        } else if (value == null) {
            r += "=null";
        }
        return r;
    }

}
