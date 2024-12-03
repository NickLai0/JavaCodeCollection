package code.java.utils;

import java.io.File;
import java.net.URL;

public class ProjectFileUtils {
    private static File sProjectClassesRootDir;

    /*
     * 通过类加载器来获取程序（.class文件）的根目录
     *
     * @return
     *  File：程序（.class文件）所在位置,如路径为：
     *       /D:/code/java/JavaCodeCollection/out/production/CodeCollection
     *  null：可能是jar包内部等，无法直接获取项目目录
     */

    public static File getProjectClassesRootDir() {
        if (sProjectClassesRootDir == null) {
            // 获取当前类加载器
            ClassLoader classLoader = Thread.currentThread()
                    .getContextClassLoader();
            // 获取资源的URL，通常是项目中的class文件所在目录
            URL resource = classLoader.getResource("");
            // 如果是file协议，则获取路径
            if (resource.getProtocol().equals("file")) {
                sProjectClassesRootDir = new File(resource.getPath());
            }
        }
        // 如果不是file协议，可能是jar包内部等，无法直接获取项目目录
        return sProjectClassesRootDir;
    }

    private static File sProjectRootDir;

    /*
     *获取项目根目录
     * @return
     *  File：整个项目的根路径,如路径为：
     *       /D:/code/java/JavaCodeCollection
     *  null：可能是jar包内部等，无法直接获取项目目录
     */
    public static File getProjectRootDir() {
        if (sProjectRootDir == null) {
            sProjectRootDir = getProjectClassesRootDir()
                    //向上提升3级，拿到项目的根目录。
                    .getParentFile()
                    .getParentFile()
                    .getParentFile();
        }
        return sProjectRootDir;
    }

    private static File sResDir;

    /**
     * @return 项目资源所在目录，
     * 如：/D:/code/java/JavaCodeCollection/src/res
     */
    public static File getResDir() {
        if (sResDir == null) {
            File projectDir = getProjectRootDir();
            // /D:/code/java/JavaCodeCollection/ + src/res
            sResDir = new File(projectDir, "src/res");
        }
        return sResDir;
    }

    private static File sImageDir;
    /**
     * @return 项目资源所在目录，
     * 如：/D:/code/java/JavaCodeCollection/src/res/images
     */
    public static File getImageDir() {
        if (sImageDir == null) {
            sImageDir = new File(getResDir(), "images");
        }
        return sImageDir;
    }

    /**
     * @return 和源代码同级的temp目录，一般用来存储和操作临时文件
     * 如：/D:/code/java/JavaCodeCollection/temp
     */
    public static File getTempDir() {
        if (sImageDir == null) {
            sImageDir = new File(getProjectRootDir(), "temp");
        }
        return sImageDir;
    }

}
