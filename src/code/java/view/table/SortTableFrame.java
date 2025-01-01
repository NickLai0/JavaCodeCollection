package code.java.view.table;

import code.java.utils.FrameUtils;
import code.java.view.table.tableModel.SortableTableModel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
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
public class SortTableFrame extends JFrame {

    // 定义一维数据作为列标题
    Object[] columnTitle = {"姓名", "年龄", "性别"};

    // 定义二维数组作为表格数据
    Object[][] tableData = {
            new Object[]{"李清照", 29, "女"},
            new Object[]{"苏格拉底", 56, "男"},
            new Object[]{"李白", 35, "男"},
            new Object[]{"弄玉", 18, "女"},
            new Object[]{"虎头", 2, "男"}
    };

    // 以二维数组和一维数组来创建一个JTable对象
    JTable table = new JTable(tableData, columnTitle);

    // 将原表格里的model包装成新的SortableTableModel对象
    SortableTableModel sorterModel = new SortableTableModel(table.getModel());

    public SortTableFrame() {
        setupView();
        // 为每列的列头增加鼠标监听器
        table.getTableHeader()
                .addMouseListener(new DoubleClickHandler());
    }

    private void setupView() {
        // 使用包装后SortableTableModel对象作为JTable的model对象
        table.setModel(sorterModel);
        // 将JTable对象放在JScrollPane中，并将该JScrollPane显示出来
        add(new JScrollPane(table));
        pack();
    }

    //双击列头，根据列来排序。
    class DoubleClickHandler extends MouseAdapter {

        public void mouseClicked(MouseEvent event) {
            //  如果单击次数小于2，即不是双击，直接返回
            if (event.getClickCount() < 2) {
                return;
            }
            // 找出鼠标双击事件所在的列索引
            int columnIndex = table.columnAtPoint(event.getPoint());
            // 将JTable中的列索引转换成对应TableModel中的列索引
            int columnModelIndex = table.convertColumnIndexToModel(columnIndex);
            // 根据指定列进行排序
            sorterModel.sort(columnModelIndex);
        }

    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(SortTableFrame.class)
                .setTitle("可按列排序的表格(双击列头排序)");
    }
}