package code.java.view.table.renderer;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * JTable的颜色单元渲染器
 * <p>
 * This renderer renders a color value as a panel with the
 * given color.
 */
public class ColorTableCellRenderer extends HighlightBorderCellRenderer implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row, int column) {
        //设置一下背景色
        setBackground((Color) value);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

    //GenderTableCellRenderer类则是在一步进行绘制table cell
    /*@Override
    public void paint(Graphics g) {
        super.paint(g);
    }*/

}
