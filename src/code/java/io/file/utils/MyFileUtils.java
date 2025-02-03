package code.java.io.file.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class MyFileUtils {

    //给目录下的文件添加序号
    public static void addOrderNumbers4EachFile(File dir) {
        List<File> fileList = new LinkedList<>();
        for (File f : dir.listFiles()) {
            if (!f.isDirectory()) {
                fileList.add(f);
            }
        }
        double index = Math.log10(fileList.size());
        int numberCount = (int) (index + 1);
        for (int i = 0, size = fileList.size(); i < size; i++) {
            File from = fileList.get(i);
            String orderedFileName = makeOrderedFileName(i + 1, numberCount, from.getName());
            from.renameTo(new File(from.getParent(), orderedFileName));
        }
    }

    private static String makeOrderedFileName(int i, int numberLength, String rawFilename) {
        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        sb.append(i);
        while (sb.length() < numberLength) {
            sb.insert(0, '0');
        }
        sb.append(rawFilename);
        String orderedFileName = sb.toString();
        return orderedFileName;
    }

    //给目录下的文件添加序号
    public static void addOrderNumbers4EachFile(String dir) {
        addOrderNumbers4EachFile(new File(dir));
    }

}
