package code.java.view.table.JTable;

import code.java.utils.FrameUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Modify a lot by myself.
 * 演示操作JTabel：
 *      隐藏选中列、
 *      显示隐藏列(缺陷：不在原始位置显示，如A、B两列，隐藏A后再显示，A跑到B后面去)、
 *      插入选中列、
 *      删除选中行、
 *      删除选中行
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
public class DefaultTableModelTest extends JFrame {

    final int ROW_COLUMN_COUNT = 5;

    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("表格管理");

    JMenuItem hideColumnsItem = new JMenuItem("隐藏选中列");
    JMenuItem showColumnsItem = new JMenuItem("显示隐藏列");
    JMenuItem addColumnItem = new JMenuItem("插入选中列");
    JMenuItem addRowItem = new JMenuItem("增加行");
    JMenuItem removeRowsItem = new JMenuItem("删除选中行");

    JTable table;

    DefaultTableModel tableModel;

    {
        tableModel = new DefaultTableModel(ROW_COLUMN_COUNT, ROW_COLUMN_COUNT);
        for (int i = 0; i < ROW_COLUMN_COUNT; i++) {
            for (int j = 0; j < ROW_COLUMN_COUNT; j++) {
                tableModel.setValueAt("老单元格值 " + i + " " + j, i, j);
            }
        }
        //既可以设置TableModel，又可以设置二维数组。
        table = new JTable(tableModel);
    }

    // 被隐藏列集合
    List<TableColumn> hiddenColumns = new ArrayList<>();

    public DefaultTableModelTest() {
        // 为窗口安装菜单
        setupMenu();
        add(new JScrollPane(table), BorderLayout.CENTER);
        initListener();
        pack();
    }

    
    private void initListener() {
        hideColumnsItem.addActionListener(new HideColumnsAction());
        showColumnsItem.addActionListener(new ShowColumnsAction());
        addColumnItem.addActionListener(new AddColumnsAction());
        addRowItem.addActionListener(new AddRowAction());
        removeRowsItem.addActionListener(new RemoveRowsAction());
    }

    private void setupMenu() {
        setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(hideColumnsItem);
        menu.add(showColumnsItem);
        menu.add(addColumnItem);
        menu.add(addRowItem);
        menu.add(removeRowsItem);
    }

    class HideColumnsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取所有选中列的索引
            /*
            an array of integers containing the indices of
            all selected columns, or an empty array if no
            column is selected
            */
            int[] selected = table.getSelectedColumns();
            //从JTable中获取已选择的列的模型。
            TableColumnModel columnModel = table.getColumnModel();
            // 依次把每一个选中的列隐藏起来，并使用List保存这些列
            for (int i = selected.length - 1; i >= 0; i--) {
                TableColumn column = columnModel.getColumn(selected[i]);
                // 隐藏指定列（内部是真删除还是假删除？）
                table.removeColumn(column);
                // 把隐藏的列保存起来，确保以后可以显示出来
                hiddenColumns.add(column);
            }
        }
    }

    class ShowColumnsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 把所有隐藏列依次显示出来
            for (TableColumn tc : hiddenColumns) {
                // 依次把所有隐藏的列显示出来
                table.addColumn(tc);
            }
            // 清空保存隐藏列的List集合
            hiddenColumns.clear();
        }
    }

    class AddColumnsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取所有选中列的索引
            int[] selectedColIndexArr = table.getSelectedColumns();
            TableColumnModel cm = table.getColumnModel();
            // 依次把选中的列添加到JTable之后（添加进去后，数据结构的数据会变？）
            for (int i = selectedColIndexArr.length - 1; i >= 0; i--) {
                table.addColumn(cm.getColumn(selectedColIndexArr[i]));
            }
        }
    }

    class AddRowAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 创建一个String数组作为新增行的内容
            String[] newCells = new String[ROW_COLUMN_COUNT];
            for (int i = 0; i < newCells.length; i++) {
                newCells[i] = "新单元格值 " + tableModel.getRowCount() + " " + i;
            }
            // 向TableModel中新增一行。
            tableModel.addRow(newCells);
        }
    }

    class RemoveRowsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取所有选中行
            int[] selected = table.getSelectedRows();
            // 依次删除所有选中行
            for (int i = selected.length - 1; i >= 0; i--) {
                tableModel.removeRow(selected[i]);
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(DefaultTableModelTest.class);
    }

}
