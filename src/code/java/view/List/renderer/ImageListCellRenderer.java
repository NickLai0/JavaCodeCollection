package code.java.view.List.renderer;

import javax.swing.*;
import java.awt.*;

import static code.java.utils.ImageUtils.newImageIcon;

//ImageCellRenderer和ImageCellRenderer2都是来源疯狂Java讲义，估计俩都是一样的，暂时先放这里。
public class ImageListCellRenderer extends JPanel implements ListCellRenderer<String> {
    private ImageIcon icon;
    private String name;
    // 定义绘制单元格时的背景色
    private Color background;
    // 定义绘制单元格时的前景色
    private Color foreground;

    public Component getListCellRendererComponent(JList list
            , String value, int index
            , boolean isSelected, boolean cellHasFocus) {
        icon = newImageIcon(value + ".gif");
        name = value;
        background = isSelected ? list.getSelectionBackground()
                : list.getBackground();
        foreground = isSelected ? list.getSelectionForeground()
                : list.getForeground();
        // 返回该JPanel对象作为列表项绘制器
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
        return new Dimension(60, 80);
    }
}