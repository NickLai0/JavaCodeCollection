package code.java.view.JFileChooser.filter;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.ArrayList;

public class BaseExtensionFileFilter extends FileFilter {

    private String description;

    private ArrayList<String> suffixList = new ArrayList<>();

    //添加扩展名：.png或者png等都可以
    public void addExtensions(String... sufArr) {
        if (sufArr != null && sufArr.length > 0) {
            for (String extension : sufArr) {
                addExtension(extension);
            }
        }
    }

    //添加扩展名：.png或者png等都可以
    public void addExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return;
        }
        if (!extension.startsWith(".")) {
            extension = "." + extension;
        }
        suffixList.add(extension.toLowerCase());
    }

    // 用于设置该文件过滤器的描述文本
    public void setDescription(String aDescription) {
        description = aDescription;
    }

    // 继承FileFilter类必须实现的抽象方法，返回该文件过滤器的描述文本
    public String getDescription() {
        return description;
    }

    // 继承FileFilter类必须实现的抽象方法，判断该文件过滤器是否接受该文件
    public boolean accept(File f) {
        // 如果该文件是路径，接受该文件
        if (f.isDirectory()) return true;
        // 将文件名转为小写（全部转为小写后比较，用于忽略文件名大小写）
        String name = f.getName().toLowerCase();
        // 遍历所有可接受的扩展名，如果扩展名相同，该文件就可接受。
        for (String extension : suffixList) {
            if (name.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

}
