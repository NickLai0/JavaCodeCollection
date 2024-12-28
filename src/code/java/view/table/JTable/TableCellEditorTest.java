package code.java.view.table.JTable;

import code.java.utils.FrameUtils;
import code.java.utils.ProjectFileUtils;
import code.java.view.table.editor.ImageCellEditor;
import code.java.view.table.tableModel.GetColumnClassTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;

/**
 * Modify a lot.
 *
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
public class TableCellEditorTest extends JFrame {
    // 定义一维数据作为列标题
    String[] columnTitle = {"姓名", "年龄", "性别", "主头像", "次头像", "是否中国人"};

    // 定义二维数组作为表格数据
    Object[][] tableData = {
            new Object[]{"李清照", 29, "女", img("3.gif"), img("3.gif"), true},
            new Object[]{"苏格拉底", 56, "男", img("1.gif"), img("1.gif"), false},
            new Object[]{"李白", 35, "男", img("4.gif"), img("4.gif"), true},
            new Object[]{"弄玉", 18, "女", img("2.gif"), img("2.gif"), true},
            new Object[]{"虎头", 2, "男", img("5.gif"), img("5.gif"), false}
    };

    JTable table = new JTable(new GetColumnClassTableModel(columnTitle, tableData));

    public TableCellEditorTest() {
        table.setRowHeight(40);
        // 为ImageIcon.class类型单元格指定默认编辑器
        table.setDefaultEditor(ImageIcon.class, new ImageCellEditor());
        // 获取次要头像TableColumn
        TableColumn minorPortraitColumn = table.getColumnModel().getColumn(4);
        // 创建JComboBox对象，并添加多个图标列表项
        JComboBox<ImageIcon> cb = new JComboBox<>();
        for (int i = 1; i <= 10; i++) {
            cb.addItem(img(  i + ".gif"));
        }
        // 设置minorPortraitColumn使用基于JComboBox的DefaultCellEditor
        minorPortraitColumn.setCellEditor(new DefaultCellEditor(cb));

        // 只能选择单元
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(true);

        // 将JTable对象放在JScrollPane中，并将该JScrollPane放在窗口中显示出来
        add(new JScrollPane(table));
        pack();
    }

    //return a new ImageIcon
    static ImageIcon img(String name) {
        return new ImageIcon(ProjectFileUtils.getImageAbsPathByName(name));
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(TableCellEditorTest.class);
    }

}

