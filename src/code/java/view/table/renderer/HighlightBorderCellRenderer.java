package code.java.view.table.renderer;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;


public class HighlightBorderCellRenderer extends JPanel implements TableCellRenderer {

    //获取Table cell 渲染器组件
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {
        if (hasFocus) {
            /*
                As you can see, the renderer draws a border
                when the cell has focus. (We ask the UIManager
                for the correct border. To find the lookup key（查找关键字）,
                we peeked into（窥视一下） the source code of the
                DefaultTableCellRenderer class.)
              */
            // 设置选中状态下绘制边框
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
        } else {
            setBorder(null);
        }
        return this;//自身就是渲染组件
    }

}