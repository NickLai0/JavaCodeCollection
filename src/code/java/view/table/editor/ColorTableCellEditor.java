package code.java.view.table.editor;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * This editor pops up a color dialog
 * to edit a cell value.
 */
public class ColorTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel panel = new JPanel();

    // prepare color dialog
    private JColorChooser colorChooser = new JColorChooser();

    private JDialog colorDialog = JColorChooser.createDialog(
            null,
            "Planet Color",
            false,//非模态
            colorChooser,//基于颜色选择器所创建的dialog
            //动态代理ActionListener，且制定回调方法为stopCellEditing
            EventHandler.create(ActionListener.class, this, "stopCellEditing"),
            //动态代理ActionListener，且制定回调方法为cancelCellEditing
            EventHandler.create(ActionListener.class, this, "cancelCellEditing")
    );

    //获取表单元的编辑器部件
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        // this is where we get the current Color value.
        // We store it in the dialog in case the
        // user starts editing
        colorChooser.setColor((Color) value);
        return panel;
    }

    //获取表单元的编辑器部件
    public boolean shouldSelectCell(EventObject anEvent) {
        // start editing, showing color dialog.
        colorDialog.setVisible(true);
        // tell caller it is ok to select this cell
        return true;
    }

    /**
     * 点击颜色对话框取消按钮的回调
     * 按：这样写真不好理解！而且不灵活
     */
    public void cancelCellEditing() {
        // editing is canceled--hide dialog
        colorDialog.setVisible(false);
        super.cancelCellEditing();
    }

    /**
     * 点击颜色对话框确定按钮的回调
     * 按：这样写真不好理解！而且不灵活
     *
     * @return
     */
    public boolean stopCellEditing() {
        // editing is complete--hide dialog
        colorDialog.setVisible(false);
        super.stopCellEditing();
        // tell caller it is ok to use color value
        return true;
    }

    public Object getCellEditorValue() {
        return colorChooser.getColor();
    }

}
