package code.java.view.table.renderer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

import static code.java.utils.ImageUtils.newImageIcon;

public class GenderTableCellRenderer extends JPanel implements TableCellRenderer {

    // 定义图标的宽度和高度
    final int ICON_WIDTH = 50;
    final int ICON_HEIGHT = 50;

    private String cellValue;

    //获取Table cell 渲染器组件
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        cellValue = (String) value;
        if (hasFocus) {
            // 设置选中状态下绘制边框
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(null);
        }
        return this;//自身就是渲染组件
    }

    // 重写paint()方法，负责绘制该单元格内容
    public void paint(Graphics g) {
        if (cellValue.equals("男") || cellValue.equalsIgnoreCase("male")) {
            // 如果表格值为"男"或"male"，则绘制一个男性图标
            drawImage(g, newImageIcon("male.png").getImage());
        } else if (cellValue.equals("女") || cellValue.equalsIgnoreCase("female")) {
            // 如果表格值为"女"或"female"，则绘制一个女性图标
            drawImage(g, newImageIcon("female.png").getImage());
        }
    }

    // 绘制图标的方法
    private void drawImage(Graphics g, Image image) {
        g.drawImage(image,
                //x轴居中绘制
                (getWidth() - ICON_WIDTH) / 2
                //y 轴居中绘制
                , (getHeight() - ICON_HEIGHT) / 2,
                null
        );
    }
}