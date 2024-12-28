package code.java.view.table.editor;

import code.java.utils.ImageUtils;
import code.java.utils.PrefixAndSuffixUtils;
import code.java.view.JFileChooser.filter.CommonImageExtensionFileFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

// 扩展DefaultCellEditor来实现TableCellEditor类
public class ImageCellEditor extends DefaultCellEditor {

    // 定义文件选择器
    private JTextField tf = new JTextField(15);

    private JButton btn = new JButton("...");

    private JFileChooser fileChooser = new JFileChooser();

    public ImageCellEditor() {
        // DefaultCellEditor没无参数构造器
        // 故此显式调用父类有参数构造器。
        super(new JTextField());
        tf.setEditable(false);
        fileChooser.addChoosableFileFilter(new CommonImageExtensionFileFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        // 系统将出现一个文件选择器让用户选择图标文件。
        btn.addActionListener(e -> openFileBrowser());
    }

    // 重写TableCellEditor接口的getTableCellEditorComponent方法
    // 该方法返回单元格编辑器，该编辑器是一个JPanel，
    // 该容器包含一个文本框和一个按钮
    public Component getTableCellEditorComponent(JTable table
            , Object value, boolean isSelected, int row, int column) {
        //初始化单元格视图
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(this.tf, BorderLayout.CENTER);
        p.add(this.btn, BorderLayout.EAST);
        //初始化数据
        btn.setPreferredSize(new Dimension(20, 20));
        tf.setText(value.toString());
        //返回单元格编辑器视图
        return p;
    }

    public Object getCellEditorValue() {
        return new ImageIcon(tf.getText());
    }

    private void openFileBrowser() {
        fileChooser.setCurrentDirectory(new File("icon"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
            // 如果单击了“取消”按钮,则取消编辑
            cancelCellEditing();
        } else {
            //单击了文件选择器的“确定”按钮,则设置field的内容
            tf.setText(
                    fileChooser.getSelectedFile().getAbsolutePath()
            );
        }
    }

    //此方法替换成了        fDialog.addChoosableFileFilter(new CommonImageExtensionFileFilter());
    @Deprecated
    private void setChoosableFileFilter() {
        fileChooser.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }
                String suf = PrefixAndSuffixUtils.getExtension(f);
                if (suf != null) {
                    if (suf.equals(ImageUtils.tiff)
                            || suf.equals(ImageUtils.tif)
                            || suf.equals(ImageUtils.gif)
                            || suf.equals(ImageUtils.jpeg)
                            || suf.equals(ImageUtils.jpg)
                            || suf.equals(ImageUtils.png)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }

            public String getDescription() {
                return "有效的图片文件";
            }
        });
    }
}
