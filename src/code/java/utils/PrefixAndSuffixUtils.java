package code.java.utils;

import java.io.File;

public class PrefixAndSuffixUtils {
    public static final String DEFAULT_SEPARATOR = ".";

    // 获取文件扩展名的方法
    public static String getSuffix(File f) {
        return getSuffix(f.getName());
    }

    // 获取文件扩展名的方法
    public static String getSuffix(String filename) {
        if (filename != null && filename.length() > 0) {
            int i = filename.lastIndexOf(DEFAULT_SEPARATOR);
            if (i > 0 && i < filename.length() - 1) {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }

    public static String removeSuffix(String str) {
        return removeSuffix(str, DEFAULT_SEPARATOR);
    }

    public static String removeSuffix(String str, String suffixSeparator) {
        if (str != null) {
            int sepIndex = str.lastIndexOf(suffixSeparator);
            if (sepIndex > 0 && sepIndex < str.length() - 1) {
                return str.substring(0,sepIndex );
            }
        }
        return str;
    }

    public static String removePrefix(String str) {
        return removePrefix(str, DEFAULT_SEPARATOR);
    }

    public static String removePrefix(String str, String suffixSeparator) {
        if (str != null) {
            int lastDotIndex = str.indexOf(suffixSeparator);
            if (lastDotIndex > 0 && lastDotIndex < str.length() - 1) {
                return str.substring(lastDotIndex + 1);
            }
        }
        return str;
    }
}
