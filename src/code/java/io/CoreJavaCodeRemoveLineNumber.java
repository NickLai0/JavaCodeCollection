package code.java.io;

import code.java.utils.IOUtils;

import java.io.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

public class CoreJavaCodeRemoveLineNumber {

    public static void main(String[] args) throws IOException {
        println("Core Java code transfer. Remove line numbers.");
        print("请输入源代码绝对路径：");
        File srcFile = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        print("请输入目标存放结果目录：");
        File destDir = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        transferTo(srcFile, destDir);
    }

    //移出代码的行号，保留结果到目标目录
    private static void transferTo(File srcFile, File destDir) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(srcFile));
        PrintWriter pw = new PrintWriter(new FileWriter(new File(destDir, srcFile.getName())));
        String line;
        while ((line = br.readLine()) != null) {
            line = fixLine(line);
            if (line != null && line.length() > 0) {
                pw.println(line);
            }
        }
        IOUtils.closeQuietly(br, pw);
    }

    private static String fixLine(String line) {
        String fixedLine = line.trim();
        if (fixedLine.length() == 0) {
            return "";
        }
        int i = 0;
        char ch;
        while (i< fixedLine.length() && (ch = fixedLine.charAt(i)) >= '0' && ch <= '9') {
            i++;
        }
        if (i > 0) {
            fixedLine = fixedLine.substring(i);
        }
        return fixedLine;
    }

}
