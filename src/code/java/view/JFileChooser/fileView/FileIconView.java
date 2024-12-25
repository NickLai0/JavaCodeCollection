package code.java.view.JFileChooser.fileView;

import code.java.utils.ProjectFileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import java.io.File;

import static code.java.utils.LU.println;
import static code.java.utils.ProjectFileUtils.getImageAbsPathByName;

public class FileIconView extends FileView {

    private FileFilter filter;

    public FileIconView(FileFilter filter) {
        this.filter = filter;
    }

    // 重写该方法，为文件夹、文件设置图标
    //相对路径不显示图标,改为绝对路径。
    public Icon getIcon(File f) {
        if (!f.isDirectory() && filter.accept(f)) {
            //不显示图标。要用绝对路径？
            return new ImageIcon(getImageAbsPathByName("pict.png"));
        } else if (f.isDirectory()) {
            // 获取所有根路径
            File[] fList = File.listRoots();
            for (File tmp : fList) {
                // 如果该路径是根路径
                if (tmp.equals(f)) {
                    //不显示图标。要用绝对路径？
                    return new ImageIcon(getImageAbsPathByName("dsk.png"));
                }
            }
            return new ImageIcon(getImageAbsPathByName("folder.png"));
        } else { // 使用默认图标
            return null;
        }
    }

    // 重写该方法，为文件夹、文件设置图标、
    //图标相对路径不显示，改为绝对路径，如何才能相对路径可显示？
    public Icon getIcon2(File f) {
        if (!f.isDirectory() && filter.accept(f)) {
            println("getIcon-> 给图片显示图标");
            //不显示图标。要用绝对路径？
            return new ImageIcon("ico/pict.png");
        } else if (f.isDirectory()) {
            // 获取所有根路径
            File[] fList = File.listRoots();
            for (File tmp : fList) {
                // 如果该路径是根路径
                if (tmp.equals(f)) {
                    println("getIcon-> 给目录显示桌面图标");
                    //不显示图标。要用绝对路径？
                    return new ImageIcon("ico/dsk.png");
                }
            }
            println("getIcon-> 给目录显示文件夹图标");
            return new ImageIcon("ico/folder.png");
        } else { // 使用默认图标
            return null;
        }
    }

}
