package code.java.utils;

import javax.swing.*;

public class ImageUtils {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    //    public final static String webh = "webh";

    public static final String[] imageSuffixArr = new String[]{
            jpeg, jpg, gif, tiff, tif, png
    };

    //返回本项目的图片绝对路径,用法：getImageAbsPath("Mercury.gif")
    public static String getImageAbsPath(String imageName) {
        return ProjectFileUtils.getImageAbsPathByName(imageName);
    }

    //create a new ImageIcon
    public static ImageIcon nii(String imageName) {
        return newImageIcon(imageName);
    }

    //create a new ImageIcon
    public static ImageIcon newImageIcon(String imageName) {
        return new ImageIcon(getImageAbsPath(imageName));
    }
}
