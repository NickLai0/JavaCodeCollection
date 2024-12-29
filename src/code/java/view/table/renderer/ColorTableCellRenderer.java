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
public class ColorTableCellRenderer extends JPanel implements TableCellRenderer {

    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row, int column) {
        setBackground((Color) value);
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(null);
        }
        return this;
    }
//GenderTableCellRenderer类则是在一步进行绘制table cell
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
