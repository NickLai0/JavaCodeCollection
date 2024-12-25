package code.java.view.JFileChooser.helper;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class FileChooserPreviewHelper implements PropertyChangeListener {

    // 定义图片预览组件的大小
    final int PREVIEW_SIZE;

    //预览组件
    JLabel lPreview = new JLabel();

    public FileChooserPreviewHelper() {
        this(100);
    }

    public FileChooserPreviewHelper(int previewSize) {
        PREVIEW_SIZE = previewSize;
        // 设置预览图片组件的大小和边框
        lPreview.setPreferredSize(new Dimension(PREVIEW_SIZE, PREVIEW_SIZE));
        lPreview.setBorder(BorderFactory.createEtchedBorder());
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // JFileChooser的被选文件已经发生了改变
        if (event.getPropertyName() == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
            // 获取用户选择的新文件
            File f = (File) event.getNewValue();
            if (f == null) {
                lPreview.setIcon(null);
                return;
            }
            // 将所选文件读入ImageIcon对象中
            ImageIcon icon = new ImageIcon(f.getPath());
            // 如果图像太大，则缩小它
            if (icon.getIconWidth() > PREVIEW_SIZE) {
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(PREVIEW_SIZE, -1/*高度按宽度等比自适应？*/, Image.SCALE_DEFAULT);
                icon = new ImageIcon(scaledImg);
            }
            // 改变accessory Label的图标
            lPreview.setIcon(icon);
        }
    }

    public JLabel getPreviewLabel() {
        return lPreview;
    }

}
