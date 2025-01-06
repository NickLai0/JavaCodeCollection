package code.java.clazz.utils;

public class ClassUtils {

    //@return
    //true：普通类；
    //false：非原型（Byte、Integer、...）类、数组类、Object类、String类；
    public static boolean isNormalClass(Class clz) {
        return !clz.isPrimitive()
                && !clz.isArray()
                && !clz.equals(String.class)
                //下面这个判断是否要加上去？
                && !clz.equals(Object.class);
    }

}
