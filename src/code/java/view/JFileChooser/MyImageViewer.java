package code.java.view.JFileChooser;

import code.java.adapter.ExitActionAdapter;
import code.java.utils.FrameUtils;
import code.java.view.JFileChooser.fileView.FileIconView;
import code.java.view.JFileChooser.filter.CommonImageExtensionFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import static code.java.utils.LU.println;

/**
 * modify a lot.
 * 可预览、查看图片。
 * <p>
 * Description:
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class MyImageViewer extends JFrame {

    // 定义图片预览组件的大小
    final int PREVIEW_SIZE = 100;

    JMenuBar menuBar = new JMenuBar();
    JMenuItem openItem = new JMenuItem("打开");
    JMenuItem exitItem = new JMenuItem("Exit");

    // 该label用于显示图片
    JLabel label = new JLabel();
    //预览组件
    JLabel previewLabel = new JLabel();

    // 以当前路径创建文件选择器
    JFileChooser chooser = new JFileChooser(".");

    // 定义文件过滤器
    CommonImageExtensionFileFilter filter = new CommonImageExtensionFileFilter();

    public MyImageViewer() {
        setTitle("图片查看器");
        initUI();
        initFileChooser();
        initListener();
    }

    private void initFileChooser() {
        chooser.addChoosableFileFilter(filter);
        // 禁止“文件类型”下拉列表显示“所有文件”选项。
        chooser.setAcceptAllFileFilterUsed(false);

        //if you want to show different icons for special ﬁle types, you can install your own ﬁle view
        chooser.setFileView(new FileIconView(filter));

        // 设置预览图片组件的大小和边框
        previewLabel.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
        previewLabel.setBorder(BorderFactory.createEtchedBorder());
        // 为文件选择器指定一个预览图片的附件
        chooser.setAccessory(previewLabel);     //
    }

    private void initListener() {
        // 单击openItem菜单项显示“打开文件”的对话框
        openItem.addActionListener(openImageActionListener);
        exitItem.addActionListener(new ExitActionAdapter());
        // 用于检测被选择文件的改变事件
        chooser.addPropertyChangeListener(new FileChooserPreviewHandler());
    }

    // ------该窗口安装菜单------
    private void initUI() {
        setupMenu();
        // 添加用于显示图片的JLabel组件。
        add(new JScrollPane(label));
        setPreferredSize(new Dimension(500, 500));
        pack();
    }

    private void setupMenu() {
        JMenu menu = new JMenu("文件");
        menuBar.add(menu);
        menu.add(openItem);
        menu.add(exitItem);
        // 为退出菜单绑定事件监听器
        setJMenuBar(menuBar);
    }

    private ActionListener openImageActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 设置文件对话框的当前路径
            // chooser.setCurrentDirectory(new File("."));
            // 显示文件对话框
            int result = chooser.showDialog(MyImageViewer.this, "打开");
            // 如果用户选择了APPROVE（同意）按钮，即打开，保存的等效按钮
            if (result == JFileChooser.APPROVE_OPTION) {
                String name = chooser.getSelectedFile().getPath();
                // 显示指定图片
                label.setIcon(new ImageIcon(name));
            }
        }
    };

    private class FileChooserPreviewHandler implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent event) {
            // JFileChooser的被选文件已经发生了改变
            if (event.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
                // 获取用户选择的新文件
                File f = (File) event.getNewValue();
                if (f == null) {
                    previewLabel.setIcon(null);
                    return;
                }
                // 将所选文件读入ImageIcon对象中
                ImageIcon icon = new ImageIcon(f.getPath());
                // 如果图像太大，则缩小它
                if (icon.getIconWidth() > PREVIEW_SIZE) {
                    Image img = icon.getImage();
                    Image scaledImg = img.getScaledInstance(
                            PREVIEW_SIZE,
                            -1/*高度按宽度等比自适应？*/,
                            Image.SCALE_DEFAULT
                    );
                    icon = new ImageIcon(scaledImg);
                }
                // 改变accessory Label的图标
                previewLabel.setIcon(icon);
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(MyImageViewer.class);
    }

}