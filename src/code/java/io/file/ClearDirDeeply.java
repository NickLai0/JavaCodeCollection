package code.java.io.file;

import code.java.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static code.java.utils.LU.print;

/**
 * 深度（递归）清除当前目录下所有文件和目录
 */
public class ClearDirDeeply {

    public static void main(String[] args) throws IOException {
        print("请输入需要清空的目录（不可恢复，谨慎输入）：");
        File dir = new File(new BufferedReader(new InputStreamReader(System.in)).readLine());
        FileUtils.deleteDirRecursively(dir);
        print("清除完毕");
    }

}
