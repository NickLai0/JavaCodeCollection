package code.java.io.file.book;

import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

public class CoreJavaCodeRemoveLineNumber {

    public static void main(String[] args) throws IOException {
        println("Core Java code transfer. Remove line numbers.");
        print("请输入源代码文件或目录绝对路径：");
        File src = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        print("请输入目标存放结果目录：");
        File dest = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        removeLineNumberThenTransferToRecursively(src, dest);
    }

    //递归遍历源文件和文件夹
    private static void removeLineNumberThenTransferToRecursively(File src, File destDir) throws IOException {
        if (src.isDirectory()) {
            File subDestDir = new File(destDir, src.getName());
            FileUtils.makeDirIfDoesNotExist(subDestDir);
            for (File f : src.listFiles()) {
                removeLineNumberThenTransferToRecursively(f, subDestDir);
            }
        } else {
            removeLineNumbersAndTransferTo(src, new File(destDir, src.getName()));
        }
    }

    //移出源文件的行号后，保存到目标文件
    private static void removeLineNumbersAndTransferTo(File src, File dest) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(src));
        PrintWriter pw = new PrintWriter(new FileWriter(dest));
        String line;
        while ((line = br.readLine()) != null) {
            line = removeLineNumbers(line);
            if (line != null && line.length() > 0) {
                pw.println(line);
            }
        }
        IOUtils.closeQuietly(br, pw);
    }

    //给字符串除行号
    private static String removeLineNumbers(String line) {
        String fixedLine = line.trim();
        if (fixedLine.length() == 0) {
            return "";
        }
        int i = 0;
        char ch;
        while (i < fixedLine.length() && (ch = fixedLine.charAt(i)) >= '0' && ch <= '9') {
            i++;
        }
        if (i > 0) {
            fixedLine = fixedLine.substring(i);
        }
        return fixedLine;
    }

}
