package code.java.clazz;

import code.java.utils.BeanDumperUtils;
import code.java.utils.LU;

//演示导出一个类的信息
public class BeanDumperByUtils {
    public static void main(String args[]) {
        /*if(args.length < 1) {
            System.err.println("usage: \n" +
                    "BeanDumper fully.qualified.class");
            System.exit(0);
        }
        Class c = null;
        try {
            c = Class.forName(args[0]);
        } catch(ClassNotFoundException ex) {
            System.err.println(
                    "Couldn't find " + args[0]);
            System.exit(0);
        }
        dump(c);
        */
        //上面用命令行来操作不好演示，我改为下面的代码，用以演示。
        String result = BeanDumperUtils.dump(Frog.class);
        LU.println(Frog.class.getName());
        LU.println(result);
    }
}
