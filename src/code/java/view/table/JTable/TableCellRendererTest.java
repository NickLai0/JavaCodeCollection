package code.java.view.table.JTable;

import code.java.utils.FrameUtils;
import code.java.view.table.renderer.GenderTableCellRenderer;
import code.java.view.table.tableModel.GetColumnClassTableModel;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import static code.java.utils.ImageUtils.newImageIcon;

/**
 * I Modify a lot.
 *
 * Demonstrates how to custom TableCellRenderer
 *
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

public class TableCellRendererTest extends JFrame {

    // 定义一维数据作为列标题
    String[] columnTitle = {"姓名", "年龄", "性别", "主头像", "是否中国人"};

    // 定义二维数组作为表格数据
    Object[][] tableData = {
            new Object[]{"李清照", 29, "女", newImageIcon("3.gif"), true},
            new Object[]{"苏格拉底", 56, "男", newImageIcon("1.gif"), false},
            new Object[]{"李白", 35, "男", newImageIcon("4.gif"), true},
            new Object[]{"弄玉", 18, "女", newImageIcon("2.gif"), true},
            new Object[]{"虎头", 2, "男", newImageIcon("5.gif"), false}
    };

    // 以二维数组和一维数组来创建一个ExtendedTableModel对象
    GetColumnClassTableModel model = new GetColumnClassTableModel(columnTitle, tableData);

    JTable table = new JTable(model);

    public TableCellRendererTest() {
        table.setRowSelectionAllowed(false);
        table.setRowHeight(120);
        table.getColumnModel()
                .getColumn(2)
                .setCellRenderer(new GenderTableCellRenderer());
        // 将JTable对象放在JScrollPane中，并将该JScrollPane显示出来
        add(new JScrollPane(table));
        pack();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(TableCellRendererTest.class);
    }

}

