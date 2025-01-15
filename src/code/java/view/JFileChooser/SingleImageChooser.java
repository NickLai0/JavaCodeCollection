package code.java.view.JFileChooser;

import code.java.view.JFileChooser.filter.CommonImageExtensionFileFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class SingleImageChooser {
    private Component parent;

    // 以当前路径创建文件选择器
    JFileChooser chooser = new JFileChooser(".");

    // 创建文件过滤器
    FileFilter filter = new CommonImageExtensionFileFilter();

    public SingleImageChooser(Component parent) {
        this.parent = parent;
        chooser.addChoosableFileFilter(filter);
        // 禁止“文件类型”下拉列表中显示“所有文件”选项。
        chooser.setAcceptAllFileFilterUsed(false);
    }

    public void showImageChooser() {
        // 显示文件对话框
        int result = chooser.showDialog(parent, "选择图片");
        // 如果用户选择了APPROVE（赞同）按钮，即打开，保存等效按钮
        if (result == JFileChooser.APPROVE_OPTION) {
            if (mL != null) {
                mL.onImageSelected(chooser.getSelectedFile());
            }
        }
    }

    public interface OnImageSelectedListener {
        void onImageSelected(File file);
    }

    private OnImageSelectedListener mL;

    public void setOnImageSelectedListener(OnImageSelectedListener l) {
        mL = l;
    }

}
