package code.java.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectUtils {
    /**
     * Returns a description of the fields of a class.
     *
     * @param c the class to be described
     * @return a string containing all field types and names
     */
    public static String getFieldDescription(Class<?> c) {
        // use reflection to find types and names of fields
        var sb = new StringBuilder();
        Field[] fieldArr = c.getDeclaredFields();
        for (int i = 0; i < fieldArr.length; i++) {
            Field f = fieldArr[i];
            if ((f.getModifiers() & Modifier.STATIC) != 0) {
                sb.append("static ");
            }
            sb.append(f.getType().getName());
            sb.append(" ");
            sb.append(f.getName());
            sb.append("\n");
        }
        return sb.toString();
    }
}
