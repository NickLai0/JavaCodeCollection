package code.java.view.tree.renderer;

import javax.swing.*;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class TreeImageCellRenderer extends JPanel implements TreeCellRenderer {
    private ImageIcon icon;
    private String name;
    // 定义绘制单元格时的背景色
    private Color background;
    // 定义绘制单元格时的前景色
    private Color foreground;

    public Component getTreeCellRendererComponent(JTree tree
            , Object value, boolean sel, boolean expanded
            , boolean leaf, int row, boolean hasFocus) {
        icon = new ImageIcon("icon/" + value + ".gif");
        name = value.toString();
        background = hasFocus ? new Color(140, 200, 235)
                : new Color(255, 255, 255);
        foreground = hasFocus ? new Color(255, 255, 3)
                : new Color(0, 0, 0);
        // 返回该JPanel对象作为单元格绘制器
        return this;
    }

    // 重写paintComponent方法，改变JPanel的外观
    public void paintComponent(Graphics g) {
        int imageWidth = icon.getImage().getWidth(null);
        int imageHeight = icon.getImage().getHeight(null);
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(foreground);
        // 绘制好友图标
        g.drawImage(icon.getImage(), getWidth() / 2
                - imageWidth / 2, 10, null);
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        // 绘制好友用户名
        g.drawString(name, getWidth() / 2
                - name.length() * 10, imageHeight + 30);
    }

    // 通过该方法来设置该ImageCellRenderer的最佳大小
    public Dimension getPreferredSize() {
        return new Dimension(80, 80);
    }
}

