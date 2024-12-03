package code.java.io;

import code.java.utils.ProjectFileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static code.java.utils.LU.println;

/**
 * 测试用try()自动关闭文件流和在finally代码块里手动关闭输出流情况
 * <p>
 * 在try()关闭更便捷，但是貌似他只能关闭一个；
 * 在finally代码块里面关闭更符合谁开谁关原则，但代码稍多。
 */
public class CloseIOExamples {
    public static void main(String[] args) throws IOException {
/*
        println("测试用try()自动关闭文件流和在finally代码块里手动关闭输出流情况");
*/
        closeStyle1();
        closeStyle2();
        println("try()关闭IO string更便捷，但貌似只能关一个；finally则更灵活");
    }


    private static void closeStyle1() throws IOException {
        File file = new File(ProjectFileUtils.getTempDir(), "CloseStyle1.txt");
        try (FileOutputStream out = new FileOutputStream(file)) {
            String str = "CloseStyle1（Let the OutputStream close automatically）: \n"
                    + " try (FileOutputStream out = new FileOutputStream(new File(ProjectFileUtils.getTempDir(),\"CloseStyle1.txt\"))) {\n" +
                    "            out.write(\"CloseStyle1: \");\n" +
                    "        }";
            out.write(str.getBytes());
        }
        println("用try()来自动关闭成功：" + file);
    }


    private static void closeStyle2() throws IOException {
        String str = "File file = new File(ProjectFileUtils.getTempDir(), \"CloseStyle2.txt\");\n" +
                "        FileOutputStream out = null;\n" +
                "        try {\n" +
                "            out = new FileOutputStream(file);\n" +
                "            out.write(str.getBytes());\n" +
                "        } finally {\n" +
                "            if (out != null) {\n" +
                "                out.close();\n" +
                "            }\n" +
                "        }";
        File file = new File(ProjectFileUtils.getTempDir(), "CloseStyle2.txt");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(str.getBytes());
        } finally {
            if (out != null) {
                out.close();
            }
        }
        println("用finally来手动关闭成功：" + file);
    }

}
