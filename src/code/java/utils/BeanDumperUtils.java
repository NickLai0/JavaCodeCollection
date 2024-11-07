package code.java.utils;

import java.beans.*;
import java.lang.reflect.Method;

//演示导出一个类的信息工具类
public class BeanDumperUtils {
    public static String dump(Class bean) {
        BeanInfo bi = null;
        try {
            bi = Introspector.getBeanInfo(bean, Object.class);
        } catch (IntrospectionException ex) {
            //introspect:反省，于此该理解为反释
            return "Couldn't introspect ";
        }
        StringBuilder sb = new StringBuilder();
        PropertyDescriptor properties[] = bi.getPropertyDescriptors();
        for (int i = 0; i < properties.length; i++) {
            Class p = properties[i].getPropertyType();
            sb.append("Property type:\n " + p.getName()).append('\n');
            sb.append("Property name:\n " + properties[i].getName()).append('\n');
            //getter method.
            Method readMethod = properties[i].getReadMethod();
            if (readMethod != null)
                sb.append("Read method:\n " + readMethod).append('\n');
            //setter method.
            Method writeMethod =
                    properties[i].getWriteMethod();
            if (writeMethod != null)
                sb.append("Write method:\n " + writeMethod).append('\n');
            sb.append("====================").append('\n');
        }
        sb.append("Public methods:").append('\n');
        MethodDescriptor methods[] = bi.getMethodDescriptors();
        for (int i = 0; i < methods.length; i++)
            sb.append(methods[i].getMethod().toString()).append('\n');
        sb.append("======================").append('\n');
        sb.append("Event support:").append('\n');
        EventSetDescriptor events[] = bi.getEventSetDescriptors();
        for (int i = 0; i < events.length; i++) {
            sb.append("Listener type:\n " + events[i].getListenerType().getName()).append('\n');
            Method lm[] = events[i].getListenerMethods();
            for (int j = 0; j < lm.length; j++)
                sb.append("Listener method:\n " + lm[j].getName()).append('\n');
            MethodDescriptor lmd[] = events[i].getListenerMethodDescriptors();
            for (int j = 0; j < lmd.length; j++)
                sb.append("Method descriptor:\n " + lmd[j].getMethod()).append('\n');
            Method addListener = events[i].getAddListenerMethod();
            sb.append("Add Listener Method:\n " + addListener).append('\n');
            Method removeListener = events[i].getRemoveListenerMethod();
            sb.append("Remove Listener Method:\n " + removeListener).append('\n');
            sb.append("====================").append('\n');
        }
        return sb.toString();
    }
}
