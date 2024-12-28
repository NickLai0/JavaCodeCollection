package code.java.utils;

import java.io.File;

public class PrefixAndSuffixUtils {

    // 获取文件扩展名的方法
    public static String getExtension(File f) {
        return getExtension(f.getName());
    }

    // 获取文件扩展名的方法
    public static String getExtension(String filename) {
        if (filename != null && filename.length() > 0) {
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

}
