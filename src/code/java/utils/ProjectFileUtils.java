package code.java.utils;

import java.io.File;
import java.net.URL;

public class ProjectFileUtils {

    private static File sProjectClassesRootDir;
    private static File sProjectRootDir;
    private static File sResDir;
    private static File sImageDir;
    private static File sDatabaseDir;

    private static File sTempDir;

    /*
     * 通过类加载器来获取程序（.class文件）的根目录
     *
     * @return
     *  File：程序（.class文件）所在位置,如路径为：
     *       /D:/code/java/JavaCodeCollection/out/production/CodeCollection
     *  null：可能是jar包内部等，无法直接获取项目目录
     */
    static {
        // 获取当前类加载器
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 获取资源的URL，通常是项目中的class文件所在目录
        URL resource = classLoader.getResource("");
        // 如果是file协议，则获取路径
        if (resource.getProtocol().equals("file")) {
            sProjectClassesRootDir = new File(resource.getPath());
        }

        sProjectRootDir = getProjectClassesRootDir()
                //向上提升3级，拿到项目的根目录。
                .getParentFile()
                .getParentFile()
                .getParentFile();

        sTempDir = new File(sProjectRootDir, "temp");
        sResDir = new File(sProjectRootDir, "src/res");
        sImageDir = new File(sResDir, "images");
        sDatabaseDir = new File(sResDir, "db");
    }

    public static File getProjectClassesRootDir() {
        // 如果不是file协议，可能是jar包内部等，无法直接获取项目目录
        return sProjectClassesRootDir;
    }

    /*
     *获取项目根目录
     * @return
     *  File：整个项目的根路径,如路径为：
     *       /D:/code/java/JavaCodeCollection
     *  null：可能是jar包内部等，无法直接获取项目目录
     */
    public static File getProjectRootDir() {
        return sProjectRootDir;
    }

    /**
     * @return 项目资源所在目录，
     * 如：/D:/code/java/JavaCodeCollection/src/res
     */
    public static File getResDir() {
        return sResDir;
    }

    /**
     * @return 项目资源所在目录，
     * 如：/D:/code/java/JavaCodeCollection/src/res/images
     */
    public static File getImageDir() {
        return sImageDir;
    }

    /**
     * @return 和源代码同级的temp目录，一般用来存储和操作临时文件
     * 如：/D:/code/java/JavaCodeCollection/temp
     */
    public static File getTempDir() {
        return sTempDir;
    }

    public static String getImageAbsPathByName(String iconName) {
        return sImageDir.getAbsolutePath() + "\\" + iconName;
    }

    public static File getsDatabaseDir() {
        return sDatabaseDir;
    }
}
