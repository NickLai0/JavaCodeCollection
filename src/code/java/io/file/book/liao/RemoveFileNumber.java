package code.java.io.file.book.liao;

import java.io.*;

import static code.java.utils.LU.print;
import static code.java.utils.LU.println;

/**
 * 移除指定目录下所有文件","前的序号。如“07李敖有话说”目录下
 * 文件名为“001,001.2004-03-08：我终于有一个机会在这里抛头露面.docx”的
 * "001,"部分会被移除，即变成“001.2004-03-08：我终于有一个机会在这里抛头露面.docx”，
 * 内容保留。
 */
public class RemoveFileNumber {
    public static void main(String[] args) throws IOException {
        println("Remove numbers for files");
        print("Source directory:");
        File srcDir = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        print("dest directory:");
        File destDir = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        for (File srcFile : srcDir.listFiles()) {
            if (!srcFile.isFile()) {
                //Skip non file.
                continue;
            }
            String fileName = srcFile.getName();
            String newFileName = fileName.substring(fileName.indexOf(",") + 1);
            File destFile = new File(destDir, newFileName);
            if (!srcFile.renameTo(destFile)) {
                println("Failed to rename " + srcFile + " to " + destFile);
            }
        }
    }
}
